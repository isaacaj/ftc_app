package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class AutonomousV1BLUE extends OpMode {

    public long startTime;

    public double speed = 0.0;

    public boolean running = false;

    public AutonomousProgram autonomousProgram;

    public void setStartTime(long millis) {
        this.startTime = millis;
    }

    private DcMotor motorRF;
    private DcMotor motorRR;
    private DcMotor motorLF;
    private DcMotor motorLR;
    private DcMotor arm1;
    private Servo collectingServo;
    private Servo catapult;

    public void init() {
        motorRF = hardwareMap.dcMotor.get("motorRF");
        motorRR = hardwareMap.dcMotor.get("motorRR");
        motorLF = hardwareMap.dcMotor.get("motorLF");
        motorLR = hardwareMap.dcMotor.get("motorLR");
        arm1 = hardwareMap.dcMotor.get("arm1");
        collectingServo = hardwareMap.servo.get("collectingServo");
        catapult = hardwareMap.servo.get("catapult");

    }


    public void loop() {
        if (!running) {
            autonomousProgram = new AutonomousProgram();
            new Thread(autonomousProgram).start();
            running = true;
        }
    }

    //S. pointed to becon, these measurements are with a fresh battery, use a new battery every round
    public class AutonomousProgram implements Runnable {
        @Override
        public void run() {
            slowForward(2750, .5);             // move towards the bin
            sleep(1000);
            liftArm1(800, 1);                 //raise arm
            sleep(1000);
            //slowForward(300, .5);          // move towards bin to get ready to dump
            //sleep(1000);
            catapultForward(500, 1);       //dump climbers in bin
            sleep(1000);
            catapultBackwards(500,1);     //retract catapult\
            sleep(1000);
            //lowerArm1(500,1);           //retract arm
            sleep(1000);
            //spinRight(700, 1);          //spin to be parrel to wall
            //moveForward(300, 1);       //move to the the parking zone

        }

        public void moveForward(long durationMillis, double speed) {
            motorRF.setPower(1);
            motorRR.setPower(1);
            motorLF.setPower(-1);
            motorLR.setPower(-1);
            sleep(durationMillis);
            stopWheels();
        }

        public void slowForward(long durationMillis, double speed) {
            motorRF.setPower(0.5);
            motorRR.setPower(0.5);
            motorLF.setPower(-0.5);
            motorLR.setPower(-0.5);
            sleep(durationMillis);
            stopWheels();
        }

        public void moveBackward(long durationMillis, double speed) {
            motorRF.setPower(-1);
            motorRR.setPower(-1);
            motorLF.setPower(1);
            motorLR.setPower(1);
            sleep(durationMillis);
            stopWheels();
        }

        public void spinRight(long durationMillis, double speed) {
            motorRF.setPower(-1);
            motorRR.setPower(-1);
            motorLF.setPower(-1);
            motorLR.setPower(-1);
            sleep(durationMillis);
            stopWheels();
        }

        public void liftArm1(long durationMillis, double speed){
            arm1.setPower(-1);
            sleep(durationMillis);
            stopWheels();
        }

        public void lowerArm1(long durationMillis, double speed){
            arm1.setPower(1);
            sleep(durationMillis);
            stopWheels();
        }

        public void servoForward (long durationMillis, double speed) {
            collectingServo.setPosition(1);
            sleep(durationMillis);
            stopWheels();
        }

        public void servoBackwards (long durationMillis, double speed){
            collectingServo.setPosition(-1);
            sleep(durationMillis);
            stopWheels();
        }

        public void catapultForward (long durationMillis, double speed) {
            catapult.setPosition(0);
            sleep(durationMillis);
            stopWheels();
        }

        public void catapultBackwards (long durationMillis, double speed){
            catapult.setPosition(1);
            sleep(durationMillis);
            stopWheels();
        }

        public void spinLeft(long durationMillis, double speed) {
            motorRF.setPower(1);
            motorRR.setPower(1);
            motorLF.setPower(1);
            motorLR.setPower(1);
            sleep(durationMillis);
            stopWheels();
        }

        private void stopWheels() {
            motorRF.setPower(0);
            motorRR.setPower(0);
            motorLF.setPower(0);
            motorLR.setPower(0);
            arm1.setPower(0);
        }


        private void sleep(long durationMillis) {
            try {
                Thread.sleep(durationMillis);
            } catch (InterruptedException e) {

            }
        }
    }
}