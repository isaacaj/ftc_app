package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class AutonomousV2RED extends OpMode {

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

      //parrel to center line with right wheels on the line
      public class AutonomousProgram implements Runnable {
          @Override
          public void run() {
              slowForward(5200, 1);            //drive until even to the shelter guide line
              sleep(1000);
              spinleft(600, 1);               //turn to directly face the wall
              sleep(1000);
              liftArm1(800, 1);             //left arm to correct hight
              sleep(1000);
              moveForward(500, 0.25);        //drive up to shelter
              catapult.setPosition(0);   //dump climber into shelter
              sleep(1000);
              catapult.setPosition(1); //retract catapult
              lowerArm1(450, 1);      //retract arm if needed
              spinleft(300, 1);      //spin left until parrell to wall
              moveForward(800,1);   //push blocks into goal
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

          public void spinleft(long durationMillis, double speed) {
              motorRF.setPower(1);
              motorRR.setPower(1);
              motorLF.setPower(1);
              motorLR.setPower(1);
              sleep(durationMillis);
              stopWheels();
          }

          public void liftArm1(long durationMillis, double seed){
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
              catapult.setPosition(0);
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