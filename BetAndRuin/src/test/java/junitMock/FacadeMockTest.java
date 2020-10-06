package test.java.junitMock;
/**
 * FacadeMockTest: Some JUnit+Mock example for FacadeMock
 */
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.Event;
import domain.Prediction;
import domain.Question;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class FacadeMockTest {
	private String queryText = "A question";
	private Float betMinimum = 2.0f;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	DataAccess dataAccess = Mockito.mock(DataAccess.class);
	Event mockedEvent = Mockito.mock(Event.class);

	BLFacade sut = new BLFacadeImplementation(dataAccess);
	private List<Prediction> list =new ArrayList<Prediction>();
	Prediction p1=new Prediction("yes", Float.valueOf((float) 1.7));
	Prediction p2=new Prediction("No", Float.valueOf((float) 22.7));
	

	// sut.createQuestion: The event has one question with a queryText.

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		list.add(p1);
		list.add(p2);
	}

	@SuppressWarnings("unchecked")
	@Test
	@DisplayName("The event has one question with a queryText")
	void createQuestionBLMockTest1() {

		try {
			Date oneDate = sdf.parse("05/10/2022");

			// configure Mock
			Mockito.doReturn(oneDate).when(mockedEvent).getEventDate();
			Mockito.when(dataAccess.createQuestion(Mockito.any(Event.class), Mockito.any(String.class),
					Mockito.any(Float.class),Mockito.any())).thenThrow(QuestionAlreadyExist.class);

			// invoke System Under Test (sut)
			assertThrows(QuestionAlreadyExist.class, () -> sut.createQuestion(mockedEvent, queryText, betMinimum,list));

		} catch (ParseException | QuestionAlreadyExist e) {
			fail("No problems should arise: ParseException/QuestionaAlreadyExist");
		}
	}

	@Test
	@DisplayName("The event has NOT a question with a queryText")
	void createQuestionBLMocktest2() {

		try {
			Date oneDate = sdf.parse("05/10/2022");

			// configure Mock
			Mockito.doReturn(oneDate).when(mockedEvent).getEventDate();
			Mockito.doReturn(new Question(queryText, betMinimum, mockedEvent)).when(dataAccess)
					.createQuestion(Mockito.any(Event.class), Mockito.any(String.class), Mockito.any(Float.class),
							Mockito.any());

			// invoke System Under Test (sut)
			Question q = sut.createQuestion(mockedEvent, queryText, betMinimum,list);

			// verify the results
			assertNotNull(q);

			ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
			ArgumentCaptor<String> questionStringCaptor = ArgumentCaptor.forClass(String.class);
			ArgumentCaptor<Float> betMinimunCaptor = ArgumentCaptor.forClass(Float.class);
			ArgumentCaptor<List<Prediction>> predic = ArgumentCaptor.forClass(List.class);

			Mockito.verify(dataAccess, Mockito.times(1)).createQuestion(eventCaptor.capture(),
					questionStringCaptor.capture(), betMinimunCaptor.capture(),predic.capture());

			assertEquals(mockedEvent, eventCaptor.getValue());
			assertEquals(queryText, questionStringCaptor.getValue());
			assertEquals(betMinimum, betMinimunCaptor.getValue());

		} catch (ParseException | QuestionAlreadyExist | EventFinished e) {
			fail("No problems should arise: ParseException/QuestionaAlreadyExist");

		}
	}

}
