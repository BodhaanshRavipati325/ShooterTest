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
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.CircularBuffer;
import frc.team3039.robot.Constants;
import frc.team3039.robot.loops.Loop;


public class Shooter extends SubsystemBase implements Loop {
    public static Shooter mInstance = new Shooter();
    private boolean isFinished;

    public static Shooter getInstance() {
        if (mInstance == null) {
            mInstance = new Shooter();
        }
        return mInstance;
    }

    public enum ShooterControlMode{
        OPEN_LOOP,
        CALCULATE,
        HOLD_WHEN_READY,
        READY,
    }

    private TalonFX mMaster, mSlave;

    private final double SHOOTER_OUTPUT_TO_ENCODER_RATIO = 0.77; //Previous 3.0 Because 3 revolutions of the encoder was one revolution of the wheels, 24.0/36.0
    private final double TICKS_PER_ROTATION = 2048.0;

    private static final int kCalculateControlSlot = 0;
    private static final int kReadyControlSlot = 1;

    private ShooterControlMode mShooterControlMode = ShooterControlMode.OPEN_LOOP;
    private double mSetpointRPM;
    private double mLastRPMSpeed;

    private CircularBuffer mKfEstimator = new CircularBuffer(Constants.kShooterKfBufferSize);
    private boolean mOnTarget = false;
    private double mOnTargetStartTime = Double.POSITIVE_INFINITY;

    public Shooter()
    {
        mMaster = new TalonFX(7);
        mSlave = new TalonFX(8);


        mMaster.configFactoryDefault();
        mSlave.configFactoryDefault();
        mMaster.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);


        mMaster.setInverted(true);
        mMaster.setSensorPhase(true);

        mSlave.setInverted(false);
        mSlave.setSensorPhase(true);

        mMaster.setNeutralMode(NeutralMode.Coast);
        mSlave.setNeutralMode(NeutralMode.Coast);

        loadGains();

        mMaster.clearStickyFaults();
        mSlave.clearStickyFaults();

        mSlave.follow(mMaster);
    }

    public synchronized boolean isFinished() {
        return isFinished;
    }

    public synchronized void setFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public synchronized ShooterControlMode getControlMode() {
        return mShooterControlMode;
    }

    public synchronized void setControlMode(ShooterControlMode controlMode) {
        this.mShooterControlMode = controlMode;
        setFinished(false);
}

    public void loadGains(){
        mMaster.config_kF(kCalculateControlSlot, Constants.kShooterkF);
        mMaster.config_kP(kCalculateControlSlot, Constants.kShooterkP);
        mMaster.config_kI(kCalculateControlSlot, Constants.kShooterkI);
        mMaster.config_kD(kCalculateControlSlot, Constants.kShooterkD);
        mMaster.config_IntegralZone(kCalculateControlSlot, Constants.kShooterkIZone);
        mMaster.configAllowableClosedloopError(kCalculateControlSlot, 100);

        mMaster.config_kF(kReadyControlSlot, Constants.kShooterkF);
        mMaster.config_kP(kReadyControlSlot, 0.0);
        mMaster.config_kI(kReadyControlSlot, 0.0);
        mMaster.config_kD(kReadyControlSlot, 0.0);
    }

    @Override
    public void onStart(double timestamp) {
        synchronized (Shooter.this) {

        }
    }

    @Override
    public void onStop(double timestamp) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onLoop(double timestamp) {
        synchronized (Shooter.this) {
            ShooterControlMode currentControlMode = getControlMode();
            synchronized (Shooter.this) {
                if (mShooterControlMode != ShooterControlMode.OPEN_LOOP) {
                } else {
                    // Reset all state.
                    mKfEstimator.clear();
                    mOnTarget = false;
                    mOnTargetStartTime = Double.POSITIVE_INFINITY;
                }
            }
        }
    }



    public void setShooterSpeed(double speed) {
        mMaster.set(ControlMode.PercentOutput, speed);
    }

    public void resetShooterPosition() {
        mMaster.setSelectedSensorPosition(0);
    }

    public double getShooterRotations() {
        return mMaster.getSelectedSensorPosition() / SHOOTER_OUTPUT_TO_ENCODER_RATIO / TICKS_PER_ROTATION;
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
