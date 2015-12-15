package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class AutonomousVRED extends OpMode {

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

    public void init() {
        motorRF = hardwareMap.dcMotor.get("motorRF");
        motorRR = hardwareMap.dcMotor.get("motorRR");
        motorLF = hardwareMap.dcMotor.get("motorLF");
        motorLR = hardwareMap.dcMotor.get("motorLR");
    }


    public void loop() {
        if (!running) {
            autonomousProgram = new AutonomousProgram();
            new Thread(autonomousProgram).start();
            running = true;
        }
    }


    public class AutonomousProgram implements Runnable {
        @Override
        public void run() {
            sleep(10000);
            moveForward(2000, 1);
            spinLeft(700, 1);
            moveForward(3000, 1);
            spinLeft(500, 1);
            moveForward(14000, 1);
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