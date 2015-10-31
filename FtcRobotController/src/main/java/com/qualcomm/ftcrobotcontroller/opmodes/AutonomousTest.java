package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by cms_guest on 10/27/15.
 */
public class AutonomousTest extends OpMode {

    //defines motors, RF means Right front, LR means Left Rear, etc.
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
        motorRF.setPower(-100);
        motorRR.setPower(-100);
        motorLF.setPower(100);
        motorLR.setPower(100);
    }

}
