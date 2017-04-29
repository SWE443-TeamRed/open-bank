package org.sdmlib.openbank.util;

import org.sdmlib.models.pattern.PatternObject;
import org.sdmlib.openbank.FeeValue;
import org.sdmlib.openbank.TransactionTypeEnum;
import org.sdmlib.models.pattern.AttributeConstraint;
import org.sdmlib.models.pattern.Pattern;
import java.math.BigInteger;
import org.sdmlib.openbank.util.BankPO;
import org.sdmlib.openbank.Bank;
import org.sdmlib.openbank.util.FeeValuePO;

public class FeeValuePO extends PatternObject<FeeValuePO, FeeValue>
{

    public FeeValueSet allMatches()
   {
      this.setDoAllMatches(true);
      
      FeeValueSet matches = new FeeValueSet();

      while (this.getPattern().getHasMatch())
      {
         matches.add((FeeValue) this.getCurrentMatch());
         
         this.getPattern().findMatch();
      }
      
      return matches;
   }


   public FeeValuePO(){
      newInstance(null);
   }

   public FeeValuePO(FeeValue... hostGraphObject) {
      if(hostGraphObject==null || hostGraphObject.length<1){
         return ;
      }
      newInstance(null, hostGraphObject);
   }

   public FeeValuePO(String modifier)
   {
      this.setModifier(modifier);
   }
   public FeeValuePO createTransTypeCondition(TransactionTypeEnum value)
   {
      new AttributeConstraint()
      .withAttrName(FeeValue.PROPERTY_TRANSTYPE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public FeeValuePO createTransTypeAssignment(TransactionTypeEnum value)
   {
      new AttributeConstraint()
      .withAttrName(FeeValue.PROPERTY_TRANSTYPE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public TransactionTypeEnum getTransType()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((FeeValue) getCurrentMatch()).getTransType();
      }
      return null;
   }
   
   public FeeValuePO withTransType(TransactionTypeEnum value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((FeeValue) getCurrentMatch()).setTransType(value);
      }
      return this;
   }
   
   public FeeValuePO createPercentCondition(BigInteger value)
   {
      new AttributeConstraint()
      .withAttrName(FeeValue.PROPERTY_PERCENT)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public FeeValuePO createPercentAssignment(BigInteger value)
   {
      new AttributeConstraint()
      .withAttrName(FeeValue.PROPERTY_PERCENT)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public BigInteger getPercent()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((FeeValue) getCurrentMatch()).getPercent();
      }
      return null;
   }
   
   public FeeValuePO withPercent(BigInteger value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((FeeValue) getCurrentMatch()).setPercent(value);
      }
      return this;
   }
   
   public BankPO createBankPO()
   {
      BankPO result = new BankPO(new Bank[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(FeeValue.PROPERTY_BANK, result);
      
      return result;
   }

   public BankPO createBankPO(String modifier)
   {
      BankPO result = new BankPO(new Bank[]{});
      
      result.setModifier(modifier);
      super.hasLink(FeeValue.PROPERTY_BANK, result);
      
      return result;
   }

   public FeeValuePO createBankLink(BankPO tgt)
   {
      return hasLinkConstraint(tgt, FeeValue.PROPERTY_BANK);
   }

   public FeeValuePO createBankLink(BankPO tgt, String modifier)
   {
      return hasLinkConstraint(tgt, FeeValue.PROPERTY_BANK, modifier);
   }

   public Bank getBank()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((FeeValue) this.getCurrentMatch()).getBank();
      }
      return null;
   }

}
