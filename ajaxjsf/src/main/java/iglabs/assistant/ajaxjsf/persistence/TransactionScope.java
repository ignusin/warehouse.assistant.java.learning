package iglabs.assistant.ajaxjsf.persistence;

public interface TransactionScope {
	void run(EntityManagerAction action);
	<T> T run(EntityManagerFunc<T> func);
}
