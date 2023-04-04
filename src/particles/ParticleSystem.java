package particles;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import collisions.CollisionMaster;
import models.TexturedModel;

public class ParticleSystem {
	
	private TexturedModel model;
	private Vector3f initialPos;
	private Float numParticles;
	
	ArrayList<Particle> particles = new ArrayList<Particle>();
	
	Random rand = new Random();

	public ParticleSystem(TexturedModel model, Vector3f initialPos, Float numParticles) {
		this.model = model;
		this.initialPos = initialPos;
		this.numParticles = numParticles;
	}

	public TexturedModel getModel() {
		return model;
	}
	
	public Vector3f getPosition() {
		return initialPos;
	}
	
	public Float getNumParticles() {
		return numParticles;
	}
	
	public ArrayList<Vector3f> getOffsets() {
		ArrayList<Vector3f> os = new ArrayList<Vector3f>();
		for (int i = 0; i < particles.size(); i++) os.add(particles.get(i).getOffset());
		return os;
	}
	
	public void addParticle(Float delta, Boolean fountain) {
		Vector3f initV = new Vector3f();
		if (fountain) initV = new Vector3f((rand.nextFloat()-0.5f)*2, 10f, (rand.nextFloat()-0.5f)*2);
		else initV = new Vector3f((rand.nextFloat()-0.5f)*4, 0.01f, (rand.nextFloat()-0.5f)*4);
		Particle p = new Particle(initialPos, initV, 0.001f, delta, 9.8f, 10);
		particles.add(p);
	}
	
	public void update(Float delta, CollisionMaster cm, Float friction, Float bouncing, Boolean fountain) {
		Boolean delete;
		if (particles.size() < 2000 && Math.random() < numParticles) addParticle(delta, fountain);
		for (int i = 0; i < particles.size(); i++) {
			delete = particles.get(i).update(delta, cm, friction, bouncing);
			if (delete) {
				particles.remove(i);
				i--;
			}
		}
	}
	
}
