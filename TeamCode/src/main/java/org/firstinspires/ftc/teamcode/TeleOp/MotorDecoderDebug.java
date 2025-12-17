package org.firstinspires.ftc.teamcode.TeleOp;

//import com.acmerobotics.dashboard.FtcDashboard;
//import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

//import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name = "Motor Decode Debugger")
public class MotorDecoderDebug extends LinearOpMode {

    // Primero declaramos todas las variables que vamos a usar
    // ( Motores, servos y temporizadores)

    DcMotor leftFront;
    DcMotor rightFront;

    DcMotor leftBack;
    DcMotor rightBack;



    @Override
    public void runOpMode() throws InterruptedException {

        // Luego las asignamos a su respectivo pedazo de hardware
        leftFront = hardwareMap.dcMotor.get("fl");
        rightFront = hardwareMap.dcMotor.get("fr");
        leftBack = hardwareMap.dcMotor.get("bl");
        rightBack = hardwareMap.dcMotor.get("br");




        // Invertimos los motores de fabrica
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);

        // Hacemos esto para que por defecto, cuando alguien deje de mover el stick de motor, se frenen todos los motores y no se quede patinando
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        waitForStart();
        while (opModeIsActive()) {

            if (gamepad1.x){
                leftFront.setPower(1);
                rightFront.setPower(0);
                leftBack.setPower(0);
                rightBack.setPower(0);
            }else if (gamepad1.y){
                leftFront.setPower(0);
                rightFront.setPower(1);
                leftBack.setPower(0);
                rightBack.setPower(0);
            }else if (gamepad1.a){
                leftFront.setPower(0);
                rightFront.setPower(0);
                leftBack.setPower(1);
                rightBack.setPower(0);
            }else if(gamepad1.b){
                leftFront.setPower(0);
                rightFront.setPower(0);
                leftBack.setPower(0);
                rightBack.setPower(1);
            }else{
                leftFront.setPower(0);
                rightFront.setPower(0);
                leftBack.setPower(0);
                rightBack.setPower(0);
            }


            telemetry.addLine("Movimiento de motores");
            telemetry.addData("LeftFront Power: ", leftFront.getPower());
            telemetry.addData("RightFront Power: ", rightFront.getPower());
            telemetry.addData("LeftBack Power: ", leftBack.getPower());
            telemetry.addData("RightBack Power: ", rightBack.getPower());
            telemetry.addLine(" ");
            telemetry.addLine("Señal de encoders");
            telemetry.addData("LeftFront Data: ", leftFront.getCurrentPosition());
            telemetry.addData("RightFront Data: ", rightFront.getCurrentPosition());
            telemetry.addData("LeftBack Data: ", leftBack.getCurrentPosition());
            telemetry.addData("RightBack Data: ", rightBack.getCurrentPosition());

            telemetry.update();

        }
    }
}

