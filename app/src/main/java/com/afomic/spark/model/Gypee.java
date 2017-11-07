package com.afomic.spark.model;

/**
 * Created by afomic on 17-Oct-16.
 */
public class Gypee {
        private double totalUnit=0;
        private double totalPoint=0;
        private double ctotalUnit=0;
        private double ctotalPoint=0;
        private double Gpa=0;
        private double Cgpa=0;

        public Gypee(double totalUnit, double totalPoint) {
            addToCgpa(totalPoint,totalUnit);
        }
        public Gypee(){
            totalPoint=0;
            totalUnit=0;
        }

        public void setTotalUnit(double totalUnit) {
            this.totalUnit = totalUnit;
        }

        public void setTotalPoint(double totalPoint) {
            this.totalPoint = totalPoint;
        }

        public double getTotalUnit() {
            return totalUnit;
        }



        public double getTotalPoint() {
            return totalPoint;
        }

        public double getCtotalUnit() {
            return ctotalUnit;
        }

        public double getCtotalPoint() {
            return ctotalPoint;
        }

        public double getGpa() {
            setGpa();
            return Gpa;
        }

        public void setGpa() {
            Gpa=getTotalPoint()/getTotalUnit();
        }

        public double getCgpa() {
            setCgpa();
            return Cgpa;
        }

        public void setCgpa() {
            Cgpa =ctotalPoint/ctotalUnit;
        }
        public void addToCgpa(double point,double unit){
            ctotalPoint=point+ctotalPoint;
            ctotalUnit=unit+ctotalUnit;

        }


}
