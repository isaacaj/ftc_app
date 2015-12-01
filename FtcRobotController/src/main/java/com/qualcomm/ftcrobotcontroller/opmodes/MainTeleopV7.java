package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by cms_guest on 11/03/15.
 */
public class MainTeleopV7 extends OpMode {

    //defines motors, RF means Right front, LR means Left Rear, etc.
    private DcMotor motorRF;
    private DcMotor motorRR;
    private DcMotor motorLF;
    private DcMotor motorLR;
    private DcMotor arm1;
    private DcMotor arm2;
    private Servo brake;
    private Servo collectingServo

    //defines what what throttle, direction, left, right, etc. are (booleans, floats, etc.)
    private float throttle;
    private float direction;
    private float left;
    private float right;
    private float rStick;
    private float lStick;


    boolean buttonB = false;
    boolean buttonY = false;
    boolean buttonX = false;
    boolean buttonA = false;
    //initiates variables so they equal zero or false, so when we start nothing conflicts
    public MainTeleopV7() {
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
        arm1 = hardwareMap.dcMotor.get("arm1");
        arm2 = hardwareMap.dcMotor.get("arm2");
        brake = hardwareMap.servo.get("brake");
        collectingServo = hardwareMap.servo.get("collectingServo")

        brake.setPosition(0);
        collectingServo.setPosition(0);

    }

    @Override
    public void loop() {
        //servo
        buttonB = gamepad2.b;
        buttonY = gamepad2.y;
        buttonA = gamepad2.a;
        buttonX = gamepad2.x;

        //arm
        rStick = gamepad2.right_stick_x;
        lStick = gamepad2.left_stick_x;

        //each time loop goes through, scans gamepads and assigns the values to their variables
        throttle = gamepad1.left_stick_y;
        direction = gamepad1.left_stick_x;
        right = throttle + direction;
        left = throttle - direction;

        //takes the input and converts so it doesnt go over 1 or under -1
        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);
        rStick = Range.clip(rStick, -1, 1);
        lStick = Range.clip(lStick, -1, 1);

        // scale the joystick value to make it easier to control
        // the robot more precisely at slower speeds.
        right = (float) scaleInput(right);
        left = (float) scaleInput(left);
        rStick = (float) scaleInput(rStick)
        lStick = (float) scaleInput(lStick);

        //sets motors to do what gathered input is
        motorRF.setPower(-right);
        motorRR.setPower(-right);
        motorLF.setPower(left);
        motorLR.setPower(left);
        arm1.setPower(-rStick);
        arm2.setPower(lStick);


        if (buttonB){
            brake.setPosition(1);
        } else if (buttonY){
            brake.setPosition(0);
        }

       if (buttonA){
           collectingServo.servo
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