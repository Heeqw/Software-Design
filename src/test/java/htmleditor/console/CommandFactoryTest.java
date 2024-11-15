package htmleditor.console;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import htmleditor.command.Command;
import htmleditor.command.InsertTagCommand;
import htmleditor.model.HtmlDocument;

public class CommandFactoryTest {
    @Test
    public void testParseAppendCommand() {
        HtmlDocument document = new HtmlDocument();
        Command command = CommandFactory.parseAppendCommand(document, "append div myId parentId '这是一段文本'");
        Assertions.assertNotNull(command);
        Assertions.assertTrue(command instanceof InsertTagCommand);
        InsertTagCommand insertCommand = (InsertTagCommand) command;
        InsertTagCommand expected = new InsertTagCommand(document, "div", "myId", "parentId", "这是一段文本");
        Assertions.assertEquals(expected, insertCommand);
    }
}
