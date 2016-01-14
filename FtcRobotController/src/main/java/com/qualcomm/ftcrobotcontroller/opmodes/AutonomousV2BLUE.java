package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class AutonomousV2BLUE extends OpMode {

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

    //S.parrel to the center line, with left wheels right aganist the center line
    public class AutonomousProgram implements Runnable {
        @Override
        public void run() {
            moveForward(3600, 0.75);//drive until even to the shelter guide line
            sleep(1000);
            spinRight(450, 1);            //turn to directly face the wall
            sleep(1000);
            liftArm1(700,1);             //left arm to correct hight
            sleep(1000);
            moveForward(900, 0.75);        //drive up to shelter
            sleep(1000);
            catapultForward(500, 1);   //dump climber into shelter
            sleep(1000);
            catapultBackwards(500,1); //retract catapult
            lowerArm1(450, 1);      //retract arm if needed
            spinRight(600, 1);      //spin right until parrell to wall
            moveForward(600,1);    //push blocks into goal
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