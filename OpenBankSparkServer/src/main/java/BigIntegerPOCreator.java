import de.uniks.networkparser.IdMap;
import org.sdmlib.models.pattern.util.PatternObjectCreator;

import java.math.BigInteger;

public class BigIntegerPOCreator extends PatternObjectCreator
{
   @Override
   public Object getSendableInstance(boolean reference)
   {
      if(reference) {
          return new BigIntegerPO(new BigInteger[]{});
      } else {
          return new BigIntegerPO();
      }
   }
   
   public static IdMap createIdMap(String sessionID) {
      return CreatorCreator.createIdMap(sessionID);
   }
}
