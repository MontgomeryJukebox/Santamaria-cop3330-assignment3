package ex44;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductsTest {

    @Test
    void testToString() {
        Products p = Loader.load("exercise44_input.json");
        assert(p.toString().equals("Name: Widget\nPrice: 25.0\nQuantity: 5\n\nName: Thing\nPrice: 15.0\nQuantity: 5\n\nName: Doodad\nPrice: 5.0\nQuantity: 10\n\n"));
    }
}