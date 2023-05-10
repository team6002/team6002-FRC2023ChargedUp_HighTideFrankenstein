// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.GlobalVariables;
import frc.robot.Constants.ElbowConstants;
import frc.robot.Constants.GlobalConstants;
import frc.robot.commands.*;
import frc.robot.subsystems.SUB_Drivetrain;
import frc.robot.subsystems.SUB_Elbow;
import frc.robot.subsystems.SUB_Elevator;
import frc.robot.subsystems.SUB_FiniteStateMachine;
import frc.robot.subsystems.SUB_Intake;
import frc.robot.subsystems.SUB_IntakeCamera;

// NOTE:  Cogfnsider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AUTO_Place extends SequentialCommandGroup {
  /** Creates a new AUTO_BalanceStation. */
  public AUTO_Place(AUTO_Trajectories p_trajectories, SUB_Drivetrain p_drivetrain,
    SUB_Elbow p_elbow, SUB_Elevator p_elevator, SUB_Intake p_intake, 
    SUB_FiniteStateMachine p_finiteStateMachine,GlobalVariables p_variables, SUB_IntakeCamera p_intakeCam,
    CommandXboxController p_controller) {

    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new CMD_setDropLevel(p_variables, GlobalConstants.kElevator3rdLevel).withTimeout(3),
      new CMD_setPickUpMode(p_variables, GlobalConstants.kPickBackGroundMode).withTimeout(3),
      new CMD_setIntakeState(p_variables, GlobalConstants.kConeMode).withTimeout(3),
      new CMD_selectIntakeCommandKey(p_intake, p_variables),
      new CMD_IntakeHold(p_intake, p_variables),
      new CMD_Place3rdConeLevel(p_intake, p_elbow, p_elevator, p_finiteStateMachine, p_variables).withTimeout(3),
      new CMD_ElbowSetPosition(p_elbow, ElbowConstants.kElbowDrop).withTimeout(1),
      new CMD_IntakeDropAuto(p_intake, p_variables).withTimeout(3),
      new WaitCommand(0.2),
      new CMD_Stow(p_intake, p_elbow, p_elevator, p_finiteStateMachine, p_variables)
    );
  }
}