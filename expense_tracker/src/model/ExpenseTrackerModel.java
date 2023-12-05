package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExpenseTrackerModel {

    //encapsulation - data integrity
    private List<Transaction> transactions;
    private List<Integer> matchedFilterIndices;

    // This is applying the Observer design pattern.                          
    // Specifically, this is the Observable class. 

    public ExpenseTrackerModel() {
        transactions = new ArrayList<Transaction>();
        matchedFilterIndices = new ArrayList<Integer>();
    }

    public void addTransaction(Transaction t) {
        // Perform input validation to guarantee that all transactions added are non-null.
        if (t == null) {
            throw new IllegalArgumentException("The new transaction must be non-null.");
        }
        transactions.add(t);
        // The previous filter is no longer valid.
        matchedFilterIndices.clear();
        stateChanged(); // Notify observers of the change
    }

    public void removeTransaction(Transaction t) {
        transactions.remove(t);
        // The previous filter is no longer valid.
        matchedFilterIndices.clear();
        stateChanged(); // Notify observers of the change
    }

    public List<Transaction> getTransactions() {
        //encapsulation - data integrity
        return Collections.unmodifiableList(new ArrayList<>(transactions));
    }

    public void setMatchedFilterIndices(List<Integer> newMatchedFilterIndices) {
        // Perform input validation
        if (newMatchedFilterIndices == null) {
            throw new IllegalArgumentException("The matched filter indices list must be non-null.");
        }
        for (Integer matchedFilterIndex : newMatchedFilterIndices) {
            if ((matchedFilterIndex < 0) || (matchedFilterIndex > this.transactions.size() - 1)) {
                throw new IllegalArgumentException("Each matched filter index must be between 0 (inclusive) and the number of transactions (exclusive).");
            }
        }
        // For encapsulation, copy in the input list 
        this.matchedFilterIndices.clear();
        this.matchedFilterIndices.addAll(newMatchedFilterIndices);
        stateChanged();
    }

    public List<Integer> getMatchedFilterIndices() {
        // For encapsulation, copy out the output list
        List<Integer> copyOfMatchedFilterIndices = new ArrayList<Integer>();
        copyOfMatchedFilterIndices.addAll(this.matchedFilterIndices);
        return copyOfMatchedFilterIndices;
    }

    /**
     * Registers the given ExpenseTrackerModelListener for
     * state change events.
     *
     * @param listener The ExpenseTrackerModelListener to be registered
     * @return If the listener is non-null and not already registered,
     *         returns true. If not, returns false.
     */
    private List<ExpenseTrackerModelListener> listeners = new ArrayList<>();

    public boolean register(ExpenseTrackerModelListener listener) {
        // For the Observable class, this is one of the methods.
        //
        // TODO

        //return false;
        if (listener == null || listeners.contains(listener)) {
            // Listener is null or already registered, so registration is not successful.
            return false;
        }
        // Listener is non-null and not already registered, so add to the list.
        listeners.add(listener);
        // Registration was successful.
        return true;
    }

    public int numberOfListeners() {
        // For testing, this is one of the methods.
        //
        //TODO
        // Return the number of registered listeners
        if (listeners.size()!=0)
        {
            return listeners.size();
        }
        return 0;
    }

    public boolean containsListener(ExpenseTrackerModelListener listener) {
        // For testing, this is one of the methods.
        //
        //TODO
        return listeners.contains(listener);
        //return false;
    }

    protected void stateChanged() {
        // For the Observable class, this is one of the methods.
        //
        //TODO
        // Notify all registered listeners of the state change
        for (ExpenseTrackerModelListener listener : listeners) {
            listener.update(this);
        }
    }
}
