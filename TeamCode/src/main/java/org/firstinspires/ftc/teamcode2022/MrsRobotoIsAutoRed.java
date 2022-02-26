package org.firstinspires.ftc.teamcode2020;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "MrsRobotoIsAuto", group = "Autonomous")
public class AutoTask extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor rightWheel;
    private DcMotor leftWheel;
    private DcMotor scoop;
    private DcMotor arm;
    private DcMotor carousel;

    private float carouselPower;
    private boolean carouselOn = false;
    private boolean extraStrength = false;
    private double lp;
    private double rp;
    private double armPower;
    private double scoopPower;

    @Override
    public void runOpMode() {
        initialize();
        waitForStart();
        turnRight();
        driveFlat();
        turnLeft();
        //moveBack();
        runCarousel();
        turnLeft();
        moveBack();
        turnRight();
    }

    public void initialize(){
        InitWheels();
        InitArm();
        InitScoop();
        InitCarousel();
        reset();
        halt();
        turnLeft();
        turnRight();
        driveFlat();
        moveBack();
        runCarousel();
    }

    public void InitWheels(){
        rightWheel = hardwareMap.get(DcMotor.class, "rightWheel");
        leftWheel = hardwareMap.get(DcMotor.class, "leftWheel");

        rightWheel.setDirection(DcMotor.Direction.FORWARD);
        leftWheel.setDirection(DcMotor.Direction.REVERSE);

        rightWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void InitArm(){
        arm = hardwareMap.get(DcMotor.class, "arm");
        arm.setDirection(DcMotor.Direction.REVERSE);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void InitScoop(){
        scoop = hardwareMap.get(DcMotor.class, "scoop");
        scoop.setDirection(DcMotorSimple.Direction.FORWARD);
        scoop.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    void InitCarousel(){

        carousel = hardwareMap.get(DcMotor.class, "carousel");
        carousel.setDirection(DcMotor.Direction.REVERSE);
        carousel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        telemetry.addData("Status", "Initialized" + runtime.toString());
    }

    void reset(){

        // Stop and Reset All of the Encoders
        rightWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        // Set a Target Position for the Motors of Zero
        leftWheel.setTargetPosition(0);
        rightWheel.setTargetPosition(0);
        carousel.setTargetPosition(0);
        scoop.setTargetPosition(0);
        arm.setTargetPosition(0);

        // Runs the Current Motors to the Position Specified by .setTargetPosition(0)
        rightWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

    void halt() {
        rightWheel.setPower(0);
        leftWheel.setPower(0);
        carousel.setPower(0);

    }
    void turnLeft(){
        leftWheel.setPower(-1);
        rightWheel.setPower(1);
        //TimeUnit.SECONDS.sleep(1);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            telemetry.addData("Status: ", e);
        }
        leftWheel.setPower(0);
        rightWheel.setPower(0);

    }
    void driveFlat() {

        leftWheel.setPower(1);
        rightWheel.setPower(1);
        //TimeUnit.SECONDS.sleep(1);
        try {
            Thread.sleep(1700);
        } catch (InterruptedException e) {
            telemetry.addData("Status: ", e);
        }
        leftWheel.setPower(0);
        rightWheel.setPower(0);
    }

    void turnRight() {
        leftWheel.setPower(1);
        rightWheel.setPower(-0.7);
        //TimeUnit.SECONDS.sleep(1);
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            telemetry.addData("Status: ", e);
        }
        leftWheel.setPower(0);
        rightWheel.setPower(0);

    }
    void moveBack(){

        leftWheel.setPower(-1);
        rightWheel.setPower(-.7);
        //TimeUnit.SECONDS.sleep(1);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            telemetry.addData("Status: ", e);
        }
        leftWheel.setPower(0);
        rightWheel.setPower(0);
    }

    void runCarousel(){
        carousel.setPower(1);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            telemetry.addData("Status: ", e);
        }
        carousel.setPower(0);

    }


}
