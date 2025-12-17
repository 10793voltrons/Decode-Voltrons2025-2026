package org.firstinspires.ftc.teamcode.TeleOp;

import static java.lang.Boolean.*;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name = "Decode_V1")
public class TeleOpDecodeV1 extends LinearOpMode {

    // Primero declaramos todas las variables que vamos a usar
    // ( Motores, servos y temporizadores)

    DcMotor leftFront;
    DcMotor rightFront;
    DcMotor leftBack;
    DcMotor rightBack;
    DcMotor ll;
    DcMotor lr;
    Servo empuja;
    Servo puerta;
    Servo siguiente;


    public static double arriba = 0.0;
    public static double abajo = 0.25;
    public static double comp_abre = 0.15;
    public static double comp_cierra = 0.30;
    public static double sig_cerrado = 0.15;
    public static double sig_abierto = 0.35;
    public boolean compOpen = FALSE;
    public boolean avanza = FALSE;
    public boolean launcher_on = false;


    ElapsedTime aButton = new ElapsedTime();
    ElapsedTime bButton = new ElapsedTime();
    ElapsedTime xButton = new ElapsedTime();
    ElapsedTime yButton = new ElapsedTime();
    ElapsedTime lbump2 = new ElapsedTime();
    ElapsedTime rbump2 = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        // Luego las asignamos a su respectivo pedazo de hardware
        leftFront = hardwareMap.dcMotor.get("fl");
        rightFront = hardwareMap.dcMotor.get("fr");
        leftBack = hardwareMap.dcMotor.get("bl");
        rightBack = hardwareMap.dcMotor.get("br");
        ll = hardwareMap.dcMotor.get("ll");
        lr = hardwareMap.dcMotor.get("lr");

        empuja =hardwareMap.servo.get("empuja");
        puerta =hardwareMap.servo.get("puerta");
        siguiente =hardwareMap.servo.get("sig");

        xButton.reset(); //reiniciar temporizador
        lbump2.reset();
        rbump2.reset();


        // Invertimos los motores de fabrica
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);

        // Hacemos esto para que por defecto, cuando alguien deje de mover el stick de motor, se frenen todos los motores y no se quede patinando
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Reiniciamos los temporizadores
        aButton.reset();
        xButton.reset();
        yButton.reset();
        bButton.reset();

        double invert = 1;
        double adjust = 10;

        puerta.setPosition(comp_cierra);
        siguiente.setPosition(sig_cerrado);


        waitForStart();
        while (opModeIsActive()) {

            // Invert puede tener dos valores: 1 o -1. Al mutiplicar invert por eso, invertimos el poder que se le debe de asignar al motor
            // Adjust es un numero que se dividira entre 10, para generar un numero decimal (e.g. 0.5) entonces al multiplicar todo el valor por este, se reducira a la mitad el poder de las llantas
            rightFront.setPower((-gamepad1.left_stick_y/1.45 - gamepad1.left_stick_x/1.45 - (gamepad1.right_stick_x/1.45 * -invert)) * (adjust / 10.0));
            leftFront.setPower((-gamepad1.left_stick_y/1.45 + gamepad1.left_stick_x/1.45 + (gamepad1.right_stick_x/1.45 * -invert)) * (adjust / 10.0));
            rightBack.setPower((-gamepad1.left_stick_y/1.45 + gamepad1.left_stick_x/1.45 - (gamepad1.right_stick_x/1.45 * -invert)) * (adjust / 10.0));
            leftBack.setPower((-gamepad1.left_stick_y/1.45 - gamepad1.left_stick_x/1.45 + (gamepad1.right_stick_x/1.45 * -invert)) * (adjust / 10.0));

            // Slow Mode
            if (gamepad1.a && aButton.milliseconds() > 300) {
                if (adjust == 10) {
                    adjust = 4;
                } else {
                    adjust = 10;
                }
                aButton.reset();
            }

            // Invert Mode
            if (gamepad1.y && yButton.milliseconds() > 500) {
                if (invert == 1) {
                    leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
                    rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
                    leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
                    rightBack.setDirection(DcMotorSimple.Direction.FORWARD);
                    invert = -1;
                } else {
                    leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
                    rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
                    leftBack.setDirection(DcMotorSimple.Direction.FORWARD);
                    rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
                    invert = 1;
                }
                yButton.reset();
            }

            /** PLAYER 2 **/
            //Iniciar o detener los motores del lanzador
            if (gamepad2.y){
                launcher_on=true;
            }
            if (gamepad2.x){
                launcher_on=false;
            }

            if (launcher_on){
                ll.setPower(0.60);
                lr.setPower(-0.60);
            }else{
                ll.setPower(0.0);
                lr.setPower(0.0);
            }

            //Activación manual del lanzador
            if (gamepad2.right_trigger > 0){
                ll.setPower(0.60);
                lr.setPower(-0.60);
            }
            else {
                ll.setPower(0);
                lr.setPower(0);
            }

            //Control del servo que empuja la pelota
            if(gamepad2.left_trigger > 0){
                empuja.setPosition(arriba);
            }else{
                empuja.setPosition(abajo);
            }

            //Abrir la compuerta para siguiente pelota
            if (gamepad2.right_bumper && rbump2.milliseconds() > 500){
                if (!compOpen){
                    puerta.setPosition(comp_abre);
                    compOpen = TRUE;
                }else{
                    puerta.setPosition(comp_cierra);
                    compOpen = FALSE;
                }
                rbump2.reset();
            }

            //Avanzar la tercer pelota a la compuerta
            if (gamepad2.left_bumper && lbump2.milliseconds() > 500){
                if (!avanza){
                    siguiente.setPosition(sig_abierto);
                    avanza = TRUE;
                }else{
                    siguiente.setPosition(sig_cerrado);
                    avanza = FALSE;
                }
                lbump2.reset();
            }

            if (gamepad2.a){
                puerta.setPosition(comp_cierra);
                siguiente.setPosition(sig_cerrado);
            }

            telemetry.addData("Invert", invert);
            telemetry.addData("Slow Mode", adjust == 4);
            telemetry.addData("Vel. Launcher: ", ll.getPower());

            telemetry.update();
        }
    }
}

