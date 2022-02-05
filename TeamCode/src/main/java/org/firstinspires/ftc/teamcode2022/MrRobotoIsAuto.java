package org.firstinspires.ftc.teamcode2022;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

@Autonomous(name = "MrRobotoIsAuto", group = "Autonomous")
public class MrRobotoIsAuto extends LinearOpMode {

    private DcMotor lf;
    private DcMotor rf;
    private DcMotor lb;
    private DcMotor rb;

    private DcMotor slitherMotor;
    private DcMotor upMotor;
    private DcMotor grabMotor;
    private DcMotor carousel;

    private double lp;
    private double rp;
    private float grabPOWER;
    private float slitherPOWER;
    private float upPOWER;
    private float carouselPOWER;

    public float gropeDelay = 0.0f;
    private boolean carouselON = false;

    private float d;
    private float t;

    void main(){

    }

    @Override
    public void runOpMode() {}
    void InitWheels() {

        // Get the Motors
        // REASON: We Need to Tell the Robot Where its Motors Are so It Knows What We Are Talking About
        rf = hardwareMap.get(DcMotor.class, "rf");
        rb = hardwareMap.get(DcMotor.class, "rb");
        lf = hardwareMap.get(DcMotor.class, "lf");
        lb = hardwareMap.get(DcMotor.class, "lb");

        // Set the Motor Directions
        // REASON: Some of the Motors are Placed Backwards, so We Need to Account for that.
        rf.setDirection(DcMotor.Direction.FORWARD);
        rb.setDirection(DcMotor.Direction.FORWARD);
        lf.setDirection(DcMotor.Direction.REVERSE);
        lb.setDirection(DcMotor.Direction.REVERSE);

        // Set the Motor Behaviors
        // REASON: When We Stop Power on the Robot, the Robot Should Brake Completely
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    void InitIntake() {

        slitherMotor = hardwareMap.get(DcMotor.class, "slitherMotor");
        upMotor = hardwareMap.get(DcMotor.class, "upMotor");
        grabMotor = hardwareMap.get(DcMotor.class, "grabMotor");

        slitherMotor.setDirection(DcMotor.Direction.FORWARD);
        upMotor.setDirection(DcMotor.Direction.FORWARD);
        grabMotor.setDirection(DcMotor.Direction.FORWARD);

        slitherMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        upMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        grabMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    void InitCarousel(){

        carousel = hardwareMap.get(DcMotor.class, "c");
        carousel.setDirection(DcMotor.Direction.FORWARD);
        carousel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    void Initialize(){
        InitWheels();
        InitIntake();
        InitCarousel();
        telemetry.addData("Status: ", "Initialized");
    }

    void reset() {

        // Stop and Reset All of the Encoders
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Set a Target Position for the Motors of Zero
        rf.setTargetPosition(0);
        rb.setTargetPosition(0);
        lf.setTargetPosition(0);
        lb.setTargetPosition(0);
        carousel.setTargetPosition(0);
        slitherMotor.setTargetPosition(0);
        grabMotor.setTargetPosition(0);
        upMotor.setTargetPosition(0);

        // Runs the Current Motors to the Position Specified by .setTargetPosition(0)
        rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    void halt() {
        rf.setPower(0);
        rb.setPower(0);
        lf.setPower(0);
        lb.setPower(0);
        carousel.setPower(0);
        slitherMotor.setPower(0);
        grabMotor.setPower(0);
        upMotor.setPower(0);
    }

    void driveFlat(float vertical, float horizontal) {

        lf.setPower(Range.clip(vertical + horizontal, -1.0, 1.0));
        lb.setPower(Range.clip(vertical - horizontal, -1.0, 1.0));
        rf.setPower(Range.clip(vertical - horizontal, -1.0, 1.0));
        rb.setPower(Range.clip(vertical + horizontal, -1.0, 1.0));

        return;
    }




}
