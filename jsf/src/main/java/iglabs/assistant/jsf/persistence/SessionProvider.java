package iglabs.assistant.jsf.persistence;

public interface SessionProvider {
	void run(SessionAction action);
	<T> T run(SessionFunc<T> func);
}
