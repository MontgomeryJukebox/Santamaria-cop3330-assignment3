package ex44;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    @Test
    void search() {
        Database db = new Database("exercise44_input.json");
        assert(db.search("Thing") != null);
        assert(db.search("not listed") == null);
        assert(db.search("Thing").quantity == 5);
    }
}