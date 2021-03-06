package network;

import util.IdentityHashSet;

import java.util.Collection;
import java.util.IdentityHashMap;

/**
 * Represents an input node. An InputNode does not have connections coming in, only
 * connections going out. It does not have an activation function.
 */
public final class InputNode extends ExitOnlyNode<InputNode> {

	public InputNode(long id, Collection<Connection> outputs) {
		super(id, outputs);
	}

	private double value;

	void write(double value) {
		this.value = value;
	}

	@Override
	double read() { return value; }


	private InputNode(InputNode original,
	                 IdentityHashMap<Object, Object> clones,
	                 IdentityHashSet<Object> cloning) {
		super(original, clones, cloning);
		this.value = original.value;
	}

	@Override
	public InputNode copy(
			IdentityHashMap<Object, Object> clones,
			IdentityHashSet<Object> cloning) {
		// if this is being cloned, circular reference reached
		if (cloning.contains(this))
			return null;

		cloning.add(this);

		final InputNode clone;
		if (clones.containsKey(this))
			clone = (InputNode) clones.get(this);
		else
			clone = new InputNode(this, clones, cloning);

		cloning.remove(this);
		clones.put(this, clone);
		return clone;
	}
}
