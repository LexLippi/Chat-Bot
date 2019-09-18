package test;
import main.GameLogic;
import main.Data;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class Test_Logic {

	
	
	@Test
	void testNoCity() {
		var logic = new GameLogic();
		Assert.assertEquals("� �� ���� ������ ������. �������� ��� ���", logic.answer("����������", new Data()));
	}
	
	@Test
	void testRightCity() {
		var logic = new GameLogic();
		Assert.assertEquals("�������", logic.answer("�������", new Data()));
	}
	
	@Test
	void testCityOnWrongLetter() {
		var data = new Data();
		var logic = new GameLogic();
		logic.answer("�����", data);
		Assert.assertEquals("�����, �� ������! ������� ���", logic.answer("�������", data));
	}
	
	@Test
	void testComputerLose() {
		var logic = new GameLogic();
		Assert.assertEquals("� ��������", logic.answer("������", new Data()));
	}
}
