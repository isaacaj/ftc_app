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
            moveForward(2000, 1);             // move towards the bin
            liftArm1(1000, 1);               //raise arm
            moveForward(750, 1);            // move towards bin to get ready to dump
            servoFowards(500, 1);        //dump climbers in bin
            lowerArm1(1000, 1);           //retract arm
            spinRight(700, 1);           //spin to be parrel to wall
            moveForward(300, 1);        //move to the the parking zone

        }

        public void moveForward(long durationMillis, double speed) {
            motorRF.setPower(1);
            motorRR.setPower(1);
            motorLF.setPower(-1);
            motorLR.setPower(-1);
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

        public void liftArm1(long durationMillis, double seed){
            arm1.setPower(1);
            sleep(durationMillis);
            stopWheels();
        }

        public void lowerArm1(long durationMillis, double speed){
            arm1.setPower(-1);
            sleep(durationMillis);
            stopWheels();
        }

        public void servoFowards (long durationMillis, double speed) {
            collectingServo.setPosition(1);
            sleep(durationMillis);
            stopWheels();
        }

        public void servoBackwards (long durationMillis, double speed){
            collectingServo.setPosition(-1);
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
        }


        private void sleep(long durationMillis) {
            try {
                Thread.sleep(durationMillis);
            } catch (InterruptedException e) {

            }
        }
    }
}