package org.firstinspires.ftc.teamcode.Auto;

import static java.lang.Boolean.FALSE;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

/*
 * This is an example of a more complex path to really test the tuning.
 */

@Autonomous(name = "Decode_PerimeterBlue_V1")
public class Decode_PerimeterBlue_V1 extends LinearOpMode {

    DcMotor ll;
    DcMotor lr;
    Servo empuja;
    Servo puerta;
    Servo siguiente;


    public static double arriba = 0.02;
    public static double abajo = 0.30;
    public static double comp_abre = 0.15;
    public static double comp_cierra = 0.30;
    public static double sig_cerrado = 0.15;
    public static double sig_abierto = 0.40;
    public boolean compOpen = FALSE;
    public boolean avanza = FALSE;
    public boolean launcher_on = false;


    @Override
    public void runOpMode() throws InterruptedException {
        ll = hardwareMap.dcMotor.get("ll");
        lr = hardwareMap.dcMotor.get("lr");

        empuja =hardwareMap.servo.get("empuja");
        puerta =hardwareMap.servo.get("puerta");
        siguiente =hardwareMap.servo.get("sig");

        puerta.setPosition(comp_cierra);
        siguiente.setPosition(sig_cerrado);


        //slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        /** Creando una posición de inicio**/
        Pose2d startPose = new Pose2d(-12, 68, Math.toRadians(90));
        drive.setPoseEstimate(startPose);

        /** Creando las trayectorias **/
        TrajectorySequence traj1 = drive.trajectorySequenceBuilder(startPose) //movimiento al sumergible
                .lineToLinearHeading(new Pose2d(-10,58,Math.toRadians(90+16.5)))
                .build();

        waitForStart();

        if (isStopRequested()) {
            return;
        }

        drive.followTrajectorySequence(traj1);
        //sleep(500);

        //sleep(100);
        ll.setPower(0.56);
        lr.setPower(-0.56);
        sleep(5000);
        empuja.setPosition(arriba);
        sleep(500);
        empuja.setPosition(abajo);
        sleep(500);
        for (int i = 0; i < 2; i++) {
            puerta.setPosition(comp_abre);
            sleep(500);
            puerta.setPosition(comp_cierra);
            sleep(500);
            siguiente.setPosition(sig_abierto);
            sleep(500);
            siguiente.setPosition(sig_cerrado);
            sleep(1000);
            empuja.setPosition(arriba);
            sleep(500);
            empuja.setPosition(abajo);
            sleep(500);
        }
        sleep(3000);
        //telemetry.addData("slide pos: ", slide.getCurrentPosition());
        //telemetry.addData("slide2 pos: ", slide2.getCurrentPosition());
        telemetry.update();
    }
}


