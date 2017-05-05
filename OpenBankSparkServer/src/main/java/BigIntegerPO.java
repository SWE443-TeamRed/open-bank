import org.sdmlib.models.pattern.PatternObject;

import java.math.BigInteger;

public class BigIntegerPO extends PatternObject<BigIntegerPO, BigInteger>
{

    public BigIntegerSet allMatches()
   {
      this.setDoAllMatches(true);
      
      BigIntegerSet matches = new BigIntegerSet();

      while (this.getPattern().getHasMatch())
      {
         matches.add((BigInteger) this.getCurrentMatch());
         
         this.getPattern().findMatch();
      }
      
      return matches;
   }


   public BigIntegerPO(){
      newInstance(null);
   }

   public BigIntegerPO(BigInteger... hostGraphObject) {
      if(hostGraphObject==null || hostGraphObject.length<1){
         return ;
      }
      newInstance(null, hostGraphObject);
   }

   public BigIntegerPO(String modifier)
   {
      this.setModifier(modifier);
   }
}
