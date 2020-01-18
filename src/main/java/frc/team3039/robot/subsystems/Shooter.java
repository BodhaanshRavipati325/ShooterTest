/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team3039.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXPIDSetConfiguration;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team3039.robot.Constants;


public class Shooter extends SubsystemBase
{
    private TalonFX mMaster;
    private TalonFX mSlave;

    private final double SHOOTER_OUTPUT_TO_ENCODER_RATIO = 1.0; //Previous 3.0 Because 3 revolutions of the encoder was one revolution of the wheels, 24.0/36.0
    private final double TICKS_PER_ROTATION = 4096.0;
    private static final int kControlSlot = 0;

    public Shooter()
    {
        mMaster = new TalonFX(5);
        mSlave = new TalonFX(6);


        mMaster.configFactoryDefault();
        mSlave.configFactoryDefault();
        mMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);


        mMaster.setInverted(true);
        mMaster.setSensorPhase(true);

        mSlave.setInverted(false);
        mSlave.setSensorPhase(true);

        mMaster.setNeutralMode(NeutralMode.Coast);
        mSlave.setNeutralMode(NeutralMode.Coast);

//        mMaster.configClosedLoopPeakOutput(kControlSlot, Constants.kShooterMaxPrecentOutput);

//        mMaster.config_kF(kControlSlot, Constants.kShooterkF);
//        mMaster.config_kP(kControlSlot, Constants.kShooterkP);
//        mMaster.config_kI(kControlSlot, Constants.kShooterkI);
//        mMaster.config_kD(kControlSlot, Constants.kShooterkD);
//        mMaster.config_IntegralZone(kControlSlot, Constants.kShooterkIZone);

        mMaster.clearStickyFaults();
        mSlave.clearStickyFaults();

        mSlave.follow(mMaster);
    }


    public void setShooterSpeed(double speed) {
        mMaster.set(ControlMode.PercentOutput, speed);
    }

    public void resetShooterPosition() {
        mMaster.setSelectedSensorPosition(0);
    }

    public double getShooterRotations() {
        return mMaster.getSelectedSensorPosition() / SHOOTER_OUTPUT_TO_ENCODER_RATIO / TICKS_PER_ROTATION * 2.0;
    }

    public double getShooterRPM() {
        return mMaster.getSelectedSensorVelocity() / SHOOTER_OUTPUT_TO_ENCODER_RATIO / TICKS_PER_ROTATION * 10.0 * 60.0;
    }

    public void setShooterRPM(double rpm) {
        mMaster.set(ControlMode.Velocity, ShooterRPMToNativeUnits(rpm));
    }

    public double ShooterRPMToNativeUnits(double rpm) {
        return rpm * SHOOTER_OUTPUT_TO_ENCODER_RATIO * TICKS_PER_ROTATION / 10.0 / 60.0;
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Shooter Rotations", getShooterRotations());
        SmartDashboard.putNumber("Shooter RPM", getShooterRPM());
        SmartDashboard.putNumber("Shooter Velocity Native", mMaster.getSelectedSensorVelocity());
        SmartDashboard.putNumber("Shooter Stator Current", mMaster.getStatorCurrent());
        SmartDashboard.putNumber("Shooter Supply Current", mMaster.getSupplyCurrent());
     }
}
