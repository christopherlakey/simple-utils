package com.scaleset.utils;


import java.io.IOException;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Collection;

public class CollectionObjectOutput implements ObjectOutput {

	private boolean closed = false;

	private final Collection<Object> objects;

	public CollectionObjectOutput() {
		objects = new ArrayList<Object>();
	}

	public CollectionObjectOutput(Collection<Object> objects) {
		this.objects = objects;
	}

	@Override
	public void close() throws IOException {
		closed = true;
	}

	@Override
	public void flush() throws IOException {
	}

	@Override
	public void write(int b) throws IOException {
		add(b);
	}

	@Override
	public void write(byte[] b) throws IOException {
		add(b);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		// we should probably implement this by making a copy
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeObject(Object obj) throws IOException {
		add(obj);
	}

	@Override
	public void writeBoolean(boolean v) throws IOException {
		add(v);
	}

	@Override
	public void writeByte(int v) throws IOException {
		add(v);
	}

	@Override
	public void writeBytes(String s) throws IOException {
		add(s);
	}

	@Override
	public void writeChar(int v) throws IOException {
		add(v);
	}

	@Override
	public void writeChars(String s) throws IOException {
		add(s);
	}

	@Override
	public void writeDouble(double v) throws IOException {
		add(v);
	}

	@Override
	public void writeFloat(float v) throws IOException {
		add(v);
	}

	@Override
	public void writeInt(int v) throws IOException {
		add(v);
	}

	@Override
	public void writeLong(long v) throws IOException {
		add(v);
	}

	@Override
	public void writeShort(int v) throws IOException {
		add(v);
	}

	@Override
	public void writeUTF(String s) throws IOException {
		add(s);
	}

	protected void add(Object obj) {
		assert (closed != false);
		objects.add(obj);
	}

	public Collection<Object> getObjects() {
		return objects;
	}
}
