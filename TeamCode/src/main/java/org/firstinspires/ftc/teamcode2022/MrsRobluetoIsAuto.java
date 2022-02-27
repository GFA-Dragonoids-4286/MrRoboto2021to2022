package org.firstinspires.ftc.teamcode2022;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name="MrsRobluetoIsAuto", group="Autonomous")
public class MrsRobluetoIsAuto extends LinearOpMode {

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
    public void runOpMode() throws InterruptedException {
        Initialize();
        waitForStart();
        moveForward(1000);
        turnLeft(1000);
        moveForward(500);
        turnRight(1000);
        moveBack(1000, 0.6);
        runCarousel(10000);
        moveForward(400);
    }

    public void Initialize(){
        InitWheels();
        InitArm();
        InitScoop();
        InitCarousel();

        telemetry.addData("Status", "Initialized" + runtime.toString());
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
        carousel.setDirection(DcMotor.Direction.FORWARD);
        carousel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    void moveBack(int time, double power){
        rightWheel.setPower(-power);
        leftWheel.setPower(-power);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            telemetry.addData("Status: ", e);
        }
        rightWheel.setPower(0);
        leftWheel.setPower(0);
    }

    void moveForward(int time){
        rightWheel.setPower(1);
        leftWheel.setPower(1);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            telemetry.addData("Status: ", e);
        }
        rightWheel.setPower(0);
        leftWheel.setPower(0);
    }

    void turnLeft(int time){
        leftWheel.setPower(1);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            telemetry.addData("Status: ", e);
        }
        leftWheel.setPower(0);
    }

    void turnRight(int time){
        rightWheel.setPower(1);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            telemetry.addData("Status: ", e);
        }
        rightWheel.setPower(0);
    }


    void runCarousel(int time) {
        carousel.setPower(0.5);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            telemetry.addData("Status: ", e);
        }
        carousel.setPower(0);
    }


}
