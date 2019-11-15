/*package frc.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.Mockito;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.*;

public class HatchIntakeTest {
    
    public static void main() {
        HatchIntake hIntake = new HatchIntake(Mockito.mock(DoubleSolenoid.class));
        hIntake.setHatchPiston(true);
        assertEquals("Expected true.", true, hIntake.getHatchPistonStatus());
    }
    /*public static void main(String[] args) {
        HatchIntake hIntake = new HatchIntake(Mockito.mock(DoubleSolenoid.class));
        hIntake.setHatchPiston(true);
        assertEquals("Expected true.", true, hIntake.getHatchPistonStatus());
    }
}*/