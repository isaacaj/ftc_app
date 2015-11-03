package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.concurrent.TimeUnit;

/**
 * Created by cms_guest on 10/27/15.
 */
public class AutonomousTest extends OpMode {

    //defines motors, RF means Right front, LR means Left Rear, etc.
    private DcMotor motorRF;
    private DcMotor motorRR;
    private DcMotor motorLF;
    private DcMotor motorLR;

    private long startTime;
    private long elapsedTime;

    private boolean busy = false;

    public void init() {
        motorRF = hardwareMap.dcMotor.get("motorRF");
        motorRR = hardwareMap.dcMotor.get("motorRR");
        motorLF = hardwareMap.dcMotor.get("motorLF");
        motorLR = hardwareMap.dcMotor.get("motorLR");
        startTime = System.currentTimeMillis();
    }

    public void loop() {


        new Runnable() {
            @Override
            public void run() {
                try {
                    forward(0);
                    Thread.sleep(3000);
                    reverse(0);
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.run();



        elapsedTime = System.currentTimeMillis() - startTime;
    }

    public void forward(long millis) {
        motorRF.setPower(-1);
        motorRR.setPower(-1);
        motorLF.setPower(1);
        motorLR.setPower(1);
    }

    public void reverse(long millis) {
        motorRF.setPower(1);
        motorRR.setPower(1);
        motorLF.setPower(-1);
        motorLR.setPower(-1);
    }

    public void halt(long millis) {
        motorRF.setPower(0);
        motorRR.setPower(0);
        motorLF.setPower(0);
        motorLR.setPower(0);
    }

    public void spinright(long millis) {
        motorRF.setPower(1);
        motorRR.setPower(1);
        motorLF.setPower(1);
        motorLR.setPower(1);
    }

    public void spinleft(long millis) {
        motorRF.setPower(-1);
        motorRR.setPower(-1);
        motorLF.setPower(-1);
        motorLR.setPower(-1);
    }

}
