import de.uniks.networkparser.IdMap;
import org.sdmlib.models.pattern.util.PatternObjectCreator;

public class TransactionPOCreator extends PatternObjectCreator
{
   @Override
   public Object getSendableInstance(boolean reference)
   {
      if(reference) {
          return new TransactionPO(new Transaction[]{});
      } else {
          return new TransactionPO();
      }
   }
   
   public static IdMap createIdMap(String sessionID) {
      return CreatorCreator.createIdMap(sessionID);
   }
}
