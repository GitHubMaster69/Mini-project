
package Main;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;
import processing.core.PVector;
import processing.data.Table;
import processing.data.TableRow;

public class Main extends PApplet {

    protected final PApplet p = this;

    float angle;

    Table table;
    float r = 200;

    PImage earth;
    PShape globe;

    public void settings() {
        size(600, 600, P3D);
        earth = loadImage("resources/earth.jpg");
        // table = loadTable("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/significant_day.csv", "header");
        table = loadTable("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.csv", "header");

        noStroke();
        globe = createShape(SPHERE, r);
        globe.setTexture(earth);
    }

    public void draw() {
        background(51);
        translate(width*0.5, height*0.5);
        rotateY(angle);
        angle += 0.05;
        p.frameRate(9999);

        lights();
        fill(200);
        noStroke();
       // sphere(r);
        shape(globe);

        for (TableRow row : table.rows()) {
            float lat = row.getFloat("latitude");
            float lon = row.getFloat("longitude");
            float mag = row.getFloat("mag");

            // original version
            // float theta = radians(lat) + PI/2;

            // fix: no + PI/2 needed, since latitude is between -180 and 180 deg
            float theta = radians(lat);

            float phi = radians(lon) + PI;

            // original version
            // float x = r * sin(theta) * cos(phi);
            // float y = -r * sin(theta) * sin(phi);
            // float z = r * cos(theta);

            // fix: in OpenGL, y & z axes are flipped from math notation of spherical coordinates
            float x = r * cos(theta) * cos(phi);
            float y = -r * sin(theta);
            float z = -r * cos(theta) * sin(phi);

            PVector pos = new PVector(x, y, z);

            float h = pow(10, mag);
            float maxh = pow(10, 7);
            h = map(h, 0, maxh, 10, 100);
            PVector xaxis = new PVector(1, 0, 0);
            float angleb = PVector.angleBetween(xaxis, pos);
            PVector raxis = xaxis.cross(pos);



            pushMatrix();
            translate(x, y, z);
            rotate(angleb, raxis.x, raxis.y, raxis.z);
            fill(255);
            box(h, 5, 5);
            popMatrix();
        }
    }

    private void translate(double v, double v1) {
    }

    public static void main(String[] args){
        Main.main("Main.Main");
    }
}

/*
package Main;

import processing.core.PApplet;

import java.util.ArrayList;
public class Main extends PApplet {

    protected final PApplet p = this;

    public void settings() {
        size(1000, 500);
    }

    @Override
    public void draw() {
        p.frameRate(9999);
        background(100);
    }

    public static void main(String[] args) {
        Main.main("Main.Main");
    }
}
*/
