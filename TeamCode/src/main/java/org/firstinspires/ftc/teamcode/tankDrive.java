package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Tank Drive", group="Iterative Opmode")
@Disabled
public class tankDrive extends OpMode
{
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor lf = null;
    private DcMotor rf = null;
    private DcMotor lb = null;
    private DcMotor rb = null;
    private DcMotor slitherMotor;
    private DcMotor erectMotor;
    private DcMotor gropeMotor;
    private DcMotor carousel;

    private double lp;
    private double rp;
    private float gropePOWER;
    private float slitherPOWER;
    private float erectPOWER;
    private float carouselPOWER;
    public float gropeDelay = 0.0f;
    private boolean carouselON = false;


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
        erectMotor = hardwareMap.get(DcMotor.class, "erectMotor");
        gropeMotor = hardwareMap.get(DcMotor.class, "gropeMotor");

        slitherMotor.setDirection(DcMotor.Direction.FORWARD);
        erectMotor.setDirection(DcMotor.Direction.FORWARD);
        gropeMotor.setDirection(DcMotor.Direction.FORWARD);

        slitherMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        erectMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        gropeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    void InitCarousel(){

        carousel = hardwareMap.get(DcMotor.class, "c");
        carousel.setDirection(DcMotor.Direction.FORWARD);
        carousel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }



    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        // init systems
        InitWheels();
        InitIntake();
        InitCarousel();

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {

    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        // Setup a variable for each drive wheel to save power level for telemetry

        // POV Mode
        // - This uses basic math to combine motions and is easier to drive straight.
        /*
        d = -gamepad1.left_stick_y;
        t  =  gamepad1.right_stick_x;
        lp = Range.clip(d + t, -1.0, 1.0) ;
        rp = Range.clip(d - t, -1.0, 1.0) ;
        */
        // Tank Mode
        // - This requires no math, but it is hard to drive forward slowly and keep straight.
        lp  = -gamepad1.left_stick_y;
        rp = -gamepad1.right_stick_y;

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", lp, rp);
        // Set speed
        if (gamepad1.left_stick_button || gamepad1.right_stick_button) {

            // Send calculated power to wheels
            lb.setPower(lp/2);
            lf.setPower(lp/2);
            rb.setPower(rp/2);
            rf.setPower(rp/2);

        } else {
            lb.setPower(lp);
            lf.setPower(lp);
            rb.setPower(rp);
            rf.setPower(rp);
        }

        //Grope Motor
        if (gamepad1.a && runtime.time() > gropeDelay) {
            gropePOWER = 0.5f;
            gropeDelay = (float) (runtime.time() + 0.4f);
        } else {
            gropePOWER = 0f;
        }
        gropeMotor.setPower(gropePOWER);

        // Erect Motor
        if (gamepad1.right_trigger != 0){
            //erectPOWER = gamepad1.right_trigger;
            erectPOWER = 0.5f;
        } else if(gamepad1.left_trigger != 0){
            //erectPOWER = -gamepad1.left_trigger;
            erectPOWER = 0.5f;
        }
        erectMotor.setPower(erectPOWER);

        // Slither Motor
        if (gamepad1.right_bumper){
            slitherPOWER = 0.5f;
        }
        else if (gamepad1.left_bumper){
            slitherPOWER = -0.5f;
        }
        else
        {
            slitherPOWER = 0f;
        }
        slitherMotor.setPower(slitherPOWER);

        if (gamepad2.x && gamepad2.dpad_down && gamepad2.left_stick_button ){
            if (!carouselON) {
                carouselPOWER = 1f;
                carouselON = true;
            }
            else{
                carouselPOWER = 0f;
                carouselON = false;
            }
        }
        
    }

    void reset() {
        // Stop and Reset All of the Encoders
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slitherMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        erectMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        gropeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        carousel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        // Set a Target Position for the Motors of Zero
        rf.setTargetPosition(0);
        rb.setTargetPosition(0);
        lf.setTargetPosition(0);
        lb.setTargetPosition(0);
        slitherMotor.setTargetPosition(0);
        erectMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        gropeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        carousel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Runs the Current Motors to the Position Specified by .setTargetPosition(0)
        rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
