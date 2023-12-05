package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the data model for an expense tracker application. This class manages
 * a list of transactions and notifies observers when changes occur.
 * It encapsulates the state of the application and enforces data integrity.
 */
public class ExpenseTrackerModel {

    // List to store the transactions
    private List<Transaction> transactions;
    // List to store indices of transactions that match a certain filter
    private List<Integer> matchedFilterIndices;
    // List of observers to notify on model updates
    private List<ExpenseTrackerModelListener> listeners;

    /**
     * Constructs an ExpenseTrackerModel with no transactions and no observers.
     */
    public ExpenseTrackerModel() {
        transactions = new ArrayList<>();
        matchedFilterIndices = new ArrayList<>();
        listeners = new ArrayList<>();
    }

    /**
     * Adds a transaction to the model and notifies all observers about the state change.
     *
     * @param t the transaction to be added
     * @throws IllegalArgumentException if the transaction is null
     */
    public void addTransaction(Transaction t) {
        if (t == null) {
            throw new IllegalArgumentException("Transaction cannot be null.");
        }
        transactions.add(t);
        matchedFilterIndices.clear();
        stateChanged();
    }

    /**
     * Removes a transaction from the model and notifies all observers about the state change.
     *
     * @param t the transaction to be removed
     */
    public void removeTransaction(Transaction t) {
        transactions.remove(t);
        matchedFilterIndices.clear();
        stateChanged();
    }

    /**
     * Provides an unmodifiable view of the transactions list to prevent external modification.
     *
     * @return an unmodifiable list of transactions
     */
    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    /**
     * Sets the indices of transactions that match a certain filter and notifies all observers
     * about the state change.
     *
     * @param newMatchedFilterIndices a list of indices to be set
     * @throws IllegalArgumentException if the list is null or indices are out of range
     */
    public void setMatchedFilterIndices(List<Integer> newMatchedFilterIndices) {
        if (newMatchedFilterIndices == null) {
            throw new IllegalArgumentException("Matched filter indices cannot be null.");
        }
        for (Integer index : newMatchedFilterIndices) {
            if (index < 0 || index >= transactions.size()) {
                throw new IllegalArgumentException("Invalid index: " + index);
            }
        }
        matchedFilterIndices.clear();
        matchedFilterIndices.addAll(newMatchedFilterIndices);
        stateChanged();
    }

    /**
     * Provides an unmodifiable view of the matched filter indices list to prevent external modification.
     *
     * @return an unmodifiable list of matched filter indices
     */
    public List<Integer> getMatchedFilterIndices() {
        return Collections.unmodifiableList(matchedFilterIndices);
    }

    /**
     * Registers an observer to be notified on model state changes.
     *
     * @param listener the observer to be registered
     * @return true if registration is successful, false if the listener is null or already registered
     */
    public boolean register(ExpenseTrackerModelListener listener) {
        if (listener == null || listeners.contains(listener)) {
            return false;
        }
        listeners.add(listener);
        return true;
    }

    /**
     * Returns the number of registered observers.
     *
     * @return the number of observers
     */
    public int numberOfListeners() {
        return listeners.size();
    }

    /**
     * Checks if a specific observer is registered.
     *
     * @param listener the observer to check
     * @return true if the observer is registered, false otherwise
     */
    public boolean containsListener(ExpenseTrackerModelListener listener) {
        return listeners.contains(listener);
    }

    /**
     * Notifies all registered observers about a state change in the model.
     */
    protected void stateChanged() {
        for (ExpenseTrackerModelListener listener : listeners) {
            listener.update(this);
        }
    }
}
