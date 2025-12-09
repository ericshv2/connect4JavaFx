/**
 * Physics class - Handles gravity simulation and acceleration for coin drops
 * Provides realistic falling animation using physics calculations
 * 
 * @author Ajitesh
 * @date Dec 5, 2025
 */
public class Physics {
    
    // Gravity constant (pixels per second squared)
    private static final double GRAVITY = 1600.0;
    
    // Maximum velocity to prevent coins from moving too fast
    private static final double MAX_VELOCITY = 5000.0;
    
    /**
     * Constructor - Initializes physics engine
     */
    public Physics() {
        // Default constructor
    }
    
    /**
     * Applies gravity to the current velocity
     * 
     * @param currentVelocity Current velocity in pixels per second
     * @param deltaTime Time elapsed since last update in seconds
     * @return New velocity after applying gravity
     */
    public double applyGravity(double currentVelocity, double deltaTime) {
        // Calculate new velocity with gravity acceleration
        double newVelocity = currentVelocity + (GRAVITY * deltaTime);
        
        // Clamp velocity to maximum to prevent excessive speed
        if (newVelocity > MAX_VELOCITY) {
            newVelocity = MAX_VELOCITY;
        }
        
        return newVelocity;
    }
    
    /**
     * Calculates the distance traveled given velocity and time
     * Uses formula: distance = velocity * time
     * 
     * @param velocity Current velocity in pixels per second
     * @param deltaTime Time interval in seconds
     * @return Distance traveled in pixels
     */
    public double calculateDistance(double velocity, double deltaTime) {
        return velocity * deltaTime;
    }
    
    /**
     * Simulates acceleration over multiple steps
     * Useful for smooth animations with variable frame rates
     * 
     * @param initialVelocity Starting velocity
     * @param time Total time to simulate
     * @param steps Number of simulation steps
     * @return Final velocity after acceleration
     */
    public double accelerate(double initialVelocity, double time, int steps) {
        double velocity = initialVelocity;
        double deltaTime = time / steps;
        
        for (int i = 0; i < steps; i++) {
            velocity = applyGravity(velocity, deltaTime);
        }
        
        return velocity;
    }
    
    /**
     * Gets the gravity constant
     * @return Gravity value in pixels per second squared
     */
    public double getGravity() {
        return GRAVITY;
    }
    
    /**
     * Gets the maximum velocity limit
     * @return Maximum velocity in pixels per second
     */
    public double getMaxVelocity() {
        return MAX_VELOCITY;
    }
}