package com.example.common;

public class CommonEnum {
	
	public enum PictureType {
		FromCamera("Па»ъ", 1), 
		FromPhoto("ПаІб", 2);
		
        private String name;
        private int index;

        private PictureType(String name, int index) {
            this.name = name;
            this.index = index;
        }

        public String getName() {
            return name;
        }

        public int getIndex() {
            return index;
        }
    }
	
	public enum ImageType {
		DefectImage("Defect Image", 1), 
		Skecth("Skecth", 2),
		FloorPlan("Floor Plan", 3);

        private String name;
        private int index;

        private ImageType(String name, int index) {
            this.name = name;
            this.index = index;
        }

        public String getName() {
            return name;
        }

        public int getIndex() {
            return index;
        }
    }
	
	public enum DefectType {
		Defect("Defect", 1), 
		WindowDefect("Window Defect", 2);
        private String name;
        private int index;

        private DefectType(String name, int index) {
            this.name = name;
            this.index = index;
        }

        public String getName() {
            return name;
        }

        public int getIndex() {
            return index;
        }
    }
	
	public enum WindowType {
		Glazing("Glazing", 1), 
		Flame("Frame", 2);
        private String name;
        private int index;

        private WindowType(String name, int index) {
            this.name = name;
            this.index = index;
        }

        public String getName() {
            return name;
        }

        public int getIndex() {
            return index;
        }
    }
	
	
}
