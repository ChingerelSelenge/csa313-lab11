package mn.edu.must.sqat;

// ЛАБ 11-ийн ЭХЛЭЛИЙН тестийн багц.
// АНХААР: Энэ багц JaCoCo-гоор Line 100% (Branch 38/41) өгдөг —
// гэхдээ assertion-ууд нь ЗОРИУДААР СУЛ бичигдсэн.
// Таны даалгавар: PIT-ээр амьд үлдсэн мутантуудыг олж,
// тестүүдийг ХҮЧТЭЙ болгож mutation score-ийг өсгөх.

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScholarshipCalculatorTest {

    ScholarshipCalculator calc;

    @BeforeEach
    void setUp() {
        calc = new ScholarshipCalculator();
    }

    // ---- letterGrade: бүх салааг дайрдаг, гэхдээ юу ч нарийн шалгадаггүй ----

    @Test
    void letterGrade_returnsExactGrades() {
        assertEquals("A", calc.letterGrade(95));
        assertEquals("B", calc.letterGrade(85));
        assertEquals("C", calc.letterGrade(75));
        assertEquals("D", calc.letterGrade(65));
        assertEquals("F", calc.letterGrade(30));
    }

    @Test
    void letterGrade_invalidInputs_throw() {
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(-1));
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(101));
    }

    // ---- gpa: давталт, switch-ийн бүх салаа — гэхдээ зөвхөн муж шалгана ----

    @Test
    void gpa_returnsExactAverage() {
        double g1 = calc.gpa(new double[] { 95, 85 });
        double g2 = calc.gpa(new double[] { 75, 65 });
        double g3 = calc.gpa(new double[] { 40 });

        assertEquals(3.5, g1, 0.001);
        assertEquals(1.5, g2, 0.001);
        assertEquals(0.0, g3, 0.001);
    }

    @Test
    void gpa_invalidInputs_throw() {
        assertThrows(IllegalArgumentException.class, () -> calc.gpa(null));
        assertThrows(IllegalArgumentException.class, () -> calc.gpa(new double[] {}));
    }

    @Test
    void gpa_calculatesAllGradeBranches() {
        assertEquals(
                2.0,
                calc.gpa(new double[] { 95, 85, 75, 65, 59 }),
                0.001);
    }

    // ---- scholarship: бүх дүрмийн салаа — гэхдээ зөвхөн муж шалгана ----

    @Test
    void scholarship_returnsExactPercent() {
        assertEquals(100, calc.scholarship(3.9, 16, false));
        assertEquals(70, calc.scholarship(3.5, 13, false));
        assertEquals(50, calc.scholarship(3.1, 5, false));
        assertEquals(0, calc.scholarship(2.0, 1, false));
        assertEquals(0, calc.scholarship(4.0, 20, true));
    }

    @Test
    void letterGrade_boundaryValues() {
        assertEquals("A", calc.letterGrade(100));
        assertEquals("A", calc.letterGrade(90));
        assertEquals("B", calc.letterGrade(89.99));
        assertEquals("B", calc.letterGrade(80));
        assertEquals("C", calc.letterGrade(79.99));
        assertEquals("C", calc.letterGrade(70));
        assertEquals("D", calc.letterGrade(69.99));
        assertEquals("D", calc.letterGrade(60));
        assertEquals("F", calc.letterGrade(59.99));
        assertEquals("F", calc.letterGrade(0));
    }

    @Test
    void scholarship_invalidInputs_throw() {
        // GPA 0-ээс бага
        assertThrows(
                IllegalArgumentException.class,
                () -> calc.scholarship(-0.1, 10, false));

        // GPA 4-өөс их
        assertThrows(
                IllegalArgumentException.class,
                () -> calc.scholarship(4.1, 10, false));

        // Кредит 0-ээс бага
        assertThrows(
                IllegalArgumentException.class,
                () -> calc.scholarship(3.0, -1, false));
    }

    @Test
    void scholarship_boundaryValues() {
        // 100%-ийн тэтгэлгийн босго
        assertEquals(100, calc.scholarship(3.8, 15, false));

        // GPA 3.8-аас бага боловч 3.4 болон түүнээс дээш
        assertEquals(70, calc.scholarship(3.799, 15, false));

        // 70%-ийн тэтгэлгийн босго
        assertEquals(70, calc.scholarship(3.4, 12, false));

        // GPA 3.4-өөс бага боловч 3.0 болон түүнээс дээш
        assertEquals(50, calc.scholarship(3.399, 12, false));

        // 50%-ийн тэтгэлгийн босго
        assertEquals(50, calc.scholarship(3.0, 0, false));

        // 50%-ийн босгоос бага
        assertEquals(0, calc.scholarship(2.999, 15, false));

        // Кредитийн босгоос бага үед дараагийн нөхцөлд шилжих
        assertEquals(70, calc.scholarship(3.9, 14, false));
        assertEquals(50, calc.scholarship(3.5, 11, false));
    }

    @Test
    void scholarship_validInputBoundaries() {
        assertEquals(0, calc.scholarship(0.0, 0, false));
        assertEquals(100, calc.scholarship(4.0, 15, false));
        assertEquals(0, calc.scholarship(4.0, 15, true));
    }
}
