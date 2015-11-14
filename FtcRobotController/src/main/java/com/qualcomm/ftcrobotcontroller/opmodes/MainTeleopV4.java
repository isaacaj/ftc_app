package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by cms_guest on 11/03/15.
 */
public class MainTeleopV4 extends OpMode {

    //defines motors, RF means Right front, LR means Left Rear, etc.
    private DcMotor motorRF;
    private DcMotor motorRR;
    private DcMotor motorLF;
    private DcMotor motorLR;
    private DcMotor motorClimb;
    private DcMotor arm1;
    private DcMotor arm2;

    //defines what what throttle, direction, left, right, etc. are (booleans, floats, etc.)
    private float throttle;
    private float direction;
    private float left;
    private float right;

    boolean buttonB = false;
    boolean buttonY = false;
    boolean reverse = false;

    //initiates variables so they equal zero or false, so when we start nothing conflicts
    public MainTeleopV4() {
        throttle = 0;
        direction = 0;
        left = 0;
        right = 0;
    }

    @Override
    public void init() {
        //maps out motors so config knows where they are
        motorRF = hardwareMap.dcMotor.get("motorRF");
        motorRR = hardwareMap.dcMotor.get("motorRR");
        motorLF = hardwareMap.dcMotor.get("motorLF");
        motorLR = hardwareMap.dcMotor.get("motorLR");
        motorClimb = hardwareMap.dcMotor.get("motorClimb");
        arm1 = hardwareMap.dcMotor.get("arm1");
        arm2 = hardwareMap.dcMotor.get("arm2");
    }

    @Override
    public void loop() {
        buttonB = gamepad2.b;
        buttonY = gamepad2.y;
        reverse = gamepad2.right_bumper;

        //each time loop goes through, scans gamepads and assigns the values to their variables
        throttle = gamepad1.left_stick_y * -1;
        direction = gamepad1.left_stick_x * -1;
        right = throttle - direction;
        left = throttle + direction;

        //takes the input and converts so it doesnt go over 1 or under -1
        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);

        // scale the joystick value to make it easier to control
        // the robot more precisely at slower speeds.
        right = (float) scaleInput(right);
        left = (float) scaleInput(left);

        //sets motors to do what gathered input is
        motorRF.setPower(-right);
        motorRR.setPower(-right);
        motorLF.setPower(left);
        motorLR.setPower(left);
        motorClimb.setPower(-0.4);

        if (buttonB){
            if(reverse){
                arm1.setPower(-1);
            } else {
                arm1.setPower(1);
            }
        } else {
            arm1.setPower(0);
        }

        if (buttonY){
            if(reverse){
                arm2.setPower(-1);
            } else {
                arm2.setPower(1);
            }
        } else {
            arm2.setPower(0);
        }
    }

    /*
     * This method scales the joystick input so for low joystick values, the
	 * scaled value is less than linear.  This is to make it easier to drive
	 * the robot more precisely at slower speeds.
	 */
    double scaleInput(double dVal) {
        double[] scaleArray = {0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00};

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }
}


   // private void scaleInput()