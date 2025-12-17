package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

//@Disabled
@TeleOp(name = "LauncherMotor")

public class AvionMotor extends LinearOpMode {
    private DcMotor launcher1;
    private DcMotor launcher2;

    @Override
    public void runOpMode() throws InterruptedException {
        launcher1 = hardwareMap.dcMotor.get("lau1");
        launcher2 = hardwareMap.dcMotor.get("lau2");

        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.right_trigger > 0){
                launcher1.setPower(.7);
                launcher2.setPower(-.7);
            }
            else {
                launcher1.setPower(0);
                launcher2.setPower(0);
            }
        }
    }
}