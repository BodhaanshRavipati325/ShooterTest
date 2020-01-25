package frc.team3039.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team3039.robot.Robot;
import frc.team3039.robot.RobotContainer;
import frc.team3039.robot.subsystems.Turret;

public class SetTrackMode extends CommandBase {
    /**
     * Creates a new Track.
     */
    private Turret mTurret = new Turret();

    public void SetTurretMode() {
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        mTurret.setTrackingMode();
        mTurret.aim();
        SmartDashboard.putNumber("Target X", mTurret.getTargetX());
        SmartDashboard.putNumber("Target Y", mTurret.getTargetY());
        SmartDashboard.putNumber("Target AREA", mTurret.getTargetArea());
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        mTurret.setDriverCamMode();
        mTurret.set(ControlMode.PercentOutput, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
