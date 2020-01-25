package frc.team3039.robot.subsystems;/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team3039.robot.Constants;
import frc.team3039.robot.Robot;


public class Turret extends SubsystemBase {

    public TalonSRX turret = new  TalonSRX(4);

    public Turret() {
        turret.setSelectedSensorPosition(0);
        turret.setSensorPhase(true);
        setDriverCamMode();
    }

    public void setTrackingMode() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3); //Turns LED on
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0); //Begin Processing Vision
    }

    public void setDriverCamMode() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1); //Turns LED off
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1); //Disable Vision Processing and Doubles Exposure
    }

    public void aim() {

        double kP = Constants.kTurretkP;

        double error = getTargetX() * kP;

        if(getCurrentPosition() <= -4000) {
            turret.set(ControlMode.PercentOutput, .15);
        }
        else if(getCurrentPosition() >= 4000) {
            turret.set(ControlMode.PercentOutput, -.15);
        }
        else {
            turret.set(ControlMode.PercentOutput, error);
        }

        SmartDashboard.putNumber("Error", error);
    }

    public void set(ControlMode mode, int speed){
        turret.set(mode,speed);
    }

    public double getTargetX() {
        return Robot.targetX;
    }

    public double getCurrentPosition() {
        return turret.getSelectedSensorPosition();
    }

    public double getTargetY() {
        return Robot.targetY;
    }

    public double getTargetArea() {
        return Robot.targetArea;
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Turret Position", getCurrentPosition());
        SmartDashboard.putNumber("target Y", getTargetY());
        // double errorX = getTargetX() - getCurrentPosition();
        // SmartDashboard.putNumber("Error", errorX);
        // SmartDashboard.putNumber("TargetX", getTargetX());
    }

}

