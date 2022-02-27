package org.firstinspires.ftc.teamcode2022;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="MrsRoboto", group="Iterative Opmode")
public class MrsRoboto extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor rightWheel;
    private DcMotor leftWheel;
    private DcMotor scoop;
    private DcMotor arm;
    private DcMotor carousel;

    private double carouselPower;
    private boolean carouselOn = false;
    private boolean holdOn = false;
    private double lp;
    private double rp;
    private double armPower;
    private double scoopPower;

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

    }

    @Override
    public void init(){
        InitWheels();
        InitArm();
        InitScoop();
        InitCarousel();

        telemetry.addData("Status", "Initialized" + runtime.toString());
    }

    @Override
    public void loop(){
        double d = -gamepad1.left_stick_y;
        double t  =  gamepad1.right_stick_x;
        lp = Range.clip(d + t, -1.0, 1.0)/2 ;
        rp = Range.clip(d - t, -1.0, 1.0)/2 ;

        telemetry.addData("Status", "Run Time: " + runtime.toString());

        if (gamepad1.left_stick_button || gamepad1.right_stick_button) {
            // Send calculated power to wheels
            rp*=2;
            lp*=2;
        }

        if (gamepad2.right_bumper){
            scoopPower = .25;
        } else if (gamepad2.left_bumper){
            scoopPower = -.25;
        } else if (gamepad2.b){
            scoopPower = .45;
        } else if (gamepad2.y){
            scoopPower = .3;
        } else {
            scoopPower = 0;
        }

        if (gamepad2.right_trigger >= 0.5){
            armPower = gamepad2.right_trigger/2;
        } else if (gamepad2.left_trigger >= 0.5){
            armPower = -gamepad2.left_trigger/2;
        } else if (gamepad2.left_trigger < 0.5 && gamepad2.right_trigger < 0.5){
            armPower = 0;
        }
        /*
        if (gamepad2.a && !carouselOn) {
            carouselPower = .6;
            carouselOn = true;
        } else if (gamepad2.b && carouselOn){
            carouselPower = 0;
            carouselOn = false;
        } else if (gamepad2.x && !carouselOn){
            carouselPower = -.6;
            carouselOn = true;
        }
        */

        if (gamepad2.a){
            carouselPower = .5;
        } else if (gamepad2.x){
            carouselPower = -.5;
        } else {
            carouselPower = 0;
        }

        carousel.setPower(carouselPower);
        leftWheel.setPower(lp);
        rightWheel.setPower(rp);
        arm.setPower(armPower);
        scoop.setPower(scoopPower);

    }
}
