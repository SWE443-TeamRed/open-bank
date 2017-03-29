package org.sdmlib.openbank.util;

import org.sdmlib.models.pattern.PatternObject;
import org.sdmlib.openbank.Account;
import org.sdmlib.models.pattern.AttributeConstraint;
import org.sdmlib.models.pattern.Pattern;
import org.sdmlib.openbank.util.UserPO;
import org.sdmlib.openbank.User;
import org.sdmlib.openbank.util.AccountPO;
import org.sdmlib.openbank.util.TransactionPO;
import org.sdmlib.openbank.Transaction;
import org.sdmlib.openbank.util.TransactionSet;

public class AccountPO extends PatternObject<AccountPO, Account>
{

    public AccountSet allMatches()
   {
      this.setDoAllMatches(true);
      
      AccountSet matches = new AccountSet();

      while (this.getPattern().getHasMatch())
      {
         matches.add((Account) this.getCurrentMatch());
         
         this.getPattern().findMatch();
      }
      
      return matches;
   }


   public AccountPO(){
      newInstance(null);
   }

   public AccountPO(Account... hostGraphObject) {
      if(hostGraphObject==null || hostGraphObject.length<1){
         return ;
      }
      newInstance(null, hostGraphObject);
   }

   public AccountPO(String modifier)
   {
      this.setModifier(modifier);
   }
   
   //==========================================================================
   
   public boolean login(String username, String password)
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Account) getCurrentMatch()).login(username, password);
      }
      return false;
   }

   
   //==========================================================================
   
   public double withdraw()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Account) getCurrentMatch()).withdraw();
      }
      return 0;
   }

   
   //==========================================================================
   
   public double deposit()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Account) getCurrentMatch()).deposit();
      }
      return 0;
   }

   public AccountPO createUsernameCondition(String value)
   {
      new AttributeConstraint()
      .withAttrName(Account.PROPERTY_USERNAME)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public AccountPO createUsernameCondition(String lower, String upper)
   {
      new AttributeConstraint()
      .withAttrName(Account.PROPERTY_USERNAME)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public AccountPO createUsernameAssignment(String value)
   {
      new AttributeConstraint()
      .withAttrName(Account.PROPERTY_USERNAME)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public String getUsername()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Account) getCurrentMatch()).getUsername();
      }
      return null;
   }
   
   public AccountPO withUsername(String value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((Account) getCurrentMatch()).setUsername(value);
      }
      return this;
   }
   
   public AccountPO createPasswordCondition(String value)
   {
      new AttributeConstraint()
      .withAttrName(Account.PROPERTY_PASSWORD)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public AccountPO createPasswordCondition(String lower, String upper)
   {
      new AttributeConstraint()
      .withAttrName(Account.PROPERTY_PASSWORD)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public AccountPO createPasswordAssignment(String value)
   {
      new AttributeConstraint()
      .withAttrName(Account.PROPERTY_PASSWORD)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public String getPassword()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Account) getCurrentMatch()).getPassword();
      }
      return null;
   }
   
   public AccountPO withPassword(String value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((Account) getCurrentMatch()).setPassword(value);
      }
      return this;
   }
   
   public AccountPO createNameCondition(String value)
   {
      new AttributeConstraint()
      .withAttrName(Account.PROPERTY_NAME)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public AccountPO createNameCondition(String lower, String upper)
   {
      new AttributeConstraint()
      .withAttrName(Account.PROPERTY_NAME)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public AccountPO createNameAssignment(String value)
   {
      new AttributeConstraint()
      .withAttrName(Account.PROPERTY_NAME)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public String getName()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Account) getCurrentMatch()).getName();
      }
      return null;
   }
   
   public AccountPO withName(String value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((Account) getCurrentMatch()).setName(value);
      }
      return this;
   }
   
   public AccountPO createEmailCondition(String value)
   {
      new AttributeConstraint()
      .withAttrName(Account.PROPERTY_EMAIL)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public AccountPO createEmailCondition(String lower, String upper)
   {
      new AttributeConstraint()
      .withAttrName(Account.PROPERTY_EMAIL)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public AccountPO createEmailAssignment(String value)
   {
      new AttributeConstraint()
      .withAttrName(Account.PROPERTY_EMAIL)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public String getEmail()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Account) getCurrentMatch()).getEmail();
      }
      return null;
   }
   
   public AccountPO withEmail(String value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((Account) getCurrentMatch()).setEmail(value);
      }
      return this;
   }
   
   public AccountPO createPhoneCondition(int value)
   {
      new AttributeConstraint()
      .withAttrName(Account.PROPERTY_PHONE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public AccountPO createPhoneCondition(int lower, int upper)
   {
      new AttributeConstraint()
      .withAttrName(Account.PROPERTY_PHONE)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public AccountPO createPhoneAssignment(int value)
   {
      new AttributeConstraint()
      .withAttrName(Account.PROPERTY_PHONE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public int getPhone()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Account) getCurrentMatch()).getPhone();
      }
      return 0;
   }
   
   public AccountPO withPhone(int value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((Account) getCurrentMatch()).setPhone(value);
      }
      return this;
   }
   
   public AccountPO createBalanceCondition(double value)
   {
      new AttributeConstraint()
      .withAttrName(Account.PROPERTY_BALANCE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public AccountPO createBalanceCondition(double lower, double upper)
   {
      new AttributeConstraint()
      .withAttrName(Account.PROPERTY_BALANCE)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public AccountPO createBalanceAssignment(double value)
   {
      new AttributeConstraint()
      .withAttrName(Account.PROPERTY_BALANCE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public double getBalance()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Account) getCurrentMatch()).getBalance();
      }
      return 0;
   }
   
   public AccountPO withBalance(double value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((Account) getCurrentMatch()).setBalance(value);
      }
      return this;
   }
   
   public AccountPO createIsLoggedInCondition(boolean value)
   {
      new AttributeConstraint()
      .withAttrName(Account.PROPERTY_ISLOGGEDIN)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public AccountPO createIsLoggedInAssignment(boolean value)
   {
      new AttributeConstraint()
      .withAttrName(Account.PROPERTY_ISLOGGEDIN)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public boolean getIsLoggedIn()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Account) getCurrentMatch()).isIsLoggedIn();
      }
      return false;
   }
   
   public AccountPO withIsLoggedIn(boolean value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((Account) getCurrentMatch()).setIsLoggedIn(value);
      }
      return this;
   }
   
   public UserPO createOwnerPO()
   {
      UserPO result = new UserPO(new User[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Account.PROPERTY_OWNER, result);
      
      return result;
   }

   public UserPO createOwnerPO(String modifier)
   {
      UserPO result = new UserPO(new User[]{});
      
      result.setModifier(modifier);
      super.hasLink(Account.PROPERTY_OWNER, result);
      
      return result;
   }

   public AccountPO createOwnerLink(UserPO tgt)
   {
      return hasLinkConstraint(tgt, Account.PROPERTY_OWNER);
   }

   public AccountPO createOwnerLink(UserPO tgt, String modifier)
   {
      return hasLinkConstraint(tgt, Account.PROPERTY_OWNER, modifier);
   }

   public User getOwner()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Account) this.getCurrentMatch()).getOwner();
      }
      return null;
   }

   public TransactionPO createCreditPO()
   {
      TransactionPO result = new TransactionPO(new Transaction[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Account.PROPERTY_CREDIT, result);
      
      return result;
   }

   public TransactionPO createCreditPO(String modifier)
   {
      TransactionPO result = new TransactionPO(new Transaction[]{});
      
      result.setModifier(modifier);
      super.hasLink(Account.PROPERTY_CREDIT, result);
      
      return result;
   }

   public AccountPO createCreditLink(TransactionPO tgt)
   {
      return hasLinkConstraint(tgt, Account.PROPERTY_CREDIT);
   }

   public AccountPO createCreditLink(TransactionPO tgt, String modifier)
   {
      return hasLinkConstraint(tgt, Account.PROPERTY_CREDIT, modifier);
   }

   public TransactionSet getCredit()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Account) this.getCurrentMatch()).getCredit();
      }
      return null;
   }

   public TransactionPO createDebitPO()
   {
      TransactionPO result = new TransactionPO(new Transaction[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Account.PROPERTY_DEBIT, result);
      
      return result;
   }

   public TransactionPO createDebitPO(String modifier)
   {
      TransactionPO result = new TransactionPO(new Transaction[]{});
      
      result.setModifier(modifier);
      super.hasLink(Account.PROPERTY_DEBIT, result);
      
      return result;
   }

   public AccountPO createDebitLink(TransactionPO tgt)
   {
      return hasLinkConstraint(tgt, Account.PROPERTY_DEBIT);
   }

   public AccountPO createDebitLink(TransactionPO tgt, String modifier)
   {
      return hasLinkConstraint(tgt, Account.PROPERTY_DEBIT, modifier);
   }

   public TransactionSet getDebit()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Account) this.getCurrentMatch()).getDebit();
      }
      return null;
   }

}
