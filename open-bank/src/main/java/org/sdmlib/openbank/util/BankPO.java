package org.sdmlib.openbank.util;

import org.sdmlib.models.pattern.PatternObject;
import org.sdmlib.openbank.Bank;
import org.sdmlib.models.pattern.AttributeConstraint;
import org.sdmlib.models.pattern.Pattern;
import org.sdmlib.openbank.util.UserPO;
import org.sdmlib.openbank.User;
import org.sdmlib.openbank.util.BankPO;
import org.sdmlib.openbank.util.UserSet;
import org.sdmlib.openbank.util.TransactionPO;
import org.sdmlib.openbank.Transaction;
import org.sdmlib.openbank.util.AccountPO;
import org.sdmlib.openbank.Account;
import org.sdmlib.openbank.util.AccountSet;

import java.math.BigInteger;
import org.sdmlib.openbank.util.FeeValuePO;
import org.sdmlib.openbank.FeeValue;
import org.sdmlib.openbank.util.FeeValueSet;
import java.lang.StringBuilder;

public class BankPO extends PatternObject<BankPO, Bank>
{

    public BankSet allMatches()
   {
      this.setDoAllMatches(true);
      
      BankSet matches = new BankSet();

      while (this.getPattern().getHasMatch())
      {
         matches.add((Bank) this.getCurrentMatch());
         
         this.getPattern().findMatch();
      }
      
      return matches;
   }


   public BankPO(){
      newInstance(null);
   }

   public BankPO(Bank... hostGraphObject) {
      if(hostGraphObject==null || hostGraphObject.length<1){
         return ;
      }
      newInstance(null, hostGraphObject);
   }

   public BankPO(String modifier)
   {
      this.setModifier(modifier);
   }
   public BankPO createFeeCondition(double value)
   {
      new AttributeConstraint()
      .withAttrName(Bank.PROPERTY_FEE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public BankPO createFeeCondition(double lower, double upper)
   {
      new AttributeConstraint()
      .withAttrName(Bank.PROPERTY_FEE)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public BankPO createFeeAssignment(double value)
   {
      new AttributeConstraint()
      .withAttrName(Bank.PROPERTY_FEE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public double getFee()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Bank) getCurrentMatch()).getFee();
      }
      return 0;
   }
   
   public BankPO withFee(double value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((Bank) getCurrentMatch()).setFee(value);
      }
      return this;
   }
   
   public BankPO createBankNameCondition(String value)
   {
      new AttributeConstraint()
      .withAttrName(Bank.PROPERTY_BANKNAME)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public BankPO createBankNameCondition(String lower, String upper)
   {
      new AttributeConstraint()
      .withAttrName(Bank.PROPERTY_BANKNAME)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public BankPO createBankNameAssignment(String value)
   {
      new AttributeConstraint()
      .withAttrName(Bank.PROPERTY_BANKNAME)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public String getBankName()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Bank) getCurrentMatch()).getBankName();
      }
      return null;
   }
   
   public BankPO withBankName(String value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((Bank) getCurrentMatch()).setBankName(value);
      }
      return this;
   }
   
   public UserPO createCustomerUserPO()
   {
      UserPO result = new UserPO(new User[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Bank.PROPERTY_CUSTOMERUSER, result);
      
      return result;
   }

   public UserPO createCustomerUserPO(String modifier)
   {
      UserPO result = new UserPO(new User[]{});
      
      result.setModifier(modifier);
      super.hasLink(Bank.PROPERTY_CUSTOMERUSER, result);
      
      return result;
   }

   public BankPO createCustomerUserLink(UserPO tgt)
   {
      return hasLinkConstraint(tgt, Bank.PROPERTY_CUSTOMERUSER);
   }

   public BankPO createCustomerUserLink(UserPO tgt, String modifier)
   {
      return hasLinkConstraint(tgt, Bank.PROPERTY_CUSTOMERUSER, modifier);
   }

   public UserSet getCustomerUser()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Bank) this.getCurrentMatch()).getCustomerUser();
      }
      return null;
   }

   public TransactionPO createTransactionPO()
   {
      TransactionPO result = new TransactionPO(new Transaction[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Bank.PROPERTY_TRANSACTION, result);
      
      return result;
   }

   public TransactionPO createTransactionPO(String modifier)
   {
      TransactionPO result = new TransactionPO(new Transaction[]{});
      
      result.setModifier(modifier);
      super.hasLink(Bank.PROPERTY_TRANSACTION, result);
      
      return result;
   }

   public BankPO createTransactionLink(TransactionPO tgt)
   {
      return hasLinkConstraint(tgt, Bank.PROPERTY_TRANSACTION);
   }

   public BankPO createTransactionLink(TransactionPO tgt, String modifier)
   {
      return hasLinkConstraint(tgt, Bank.PROPERTY_TRANSACTION, modifier);
   }

   public Transaction getTransaction()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Bank) this.getCurrentMatch()).getTransaction();
      }
      return null;
   }

   public AccountPO createCustomerAccountsPO()
   {
      AccountPO result = new AccountPO(new Account[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Bank.PROPERTY_CUSTOMERACCOUNTS, result);
      
      return result;
   }

   public AccountPO createCustomerAccountsPO(String modifier)
   {
      AccountPO result = new AccountPO(new Account[]{});
      
      result.setModifier(modifier);
      super.hasLink(Bank.PROPERTY_CUSTOMERACCOUNTS, result);
      
      return result;
   }

   public BankPO createCustomerAccountsLink(AccountPO tgt)
   {
      return hasLinkConstraint(tgt, Bank.PROPERTY_CUSTOMERACCOUNTS);
   }

   public BankPO createCustomerAccountsLink(AccountPO tgt, String modifier)
   {
      return hasLinkConstraint(tgt, Bank.PROPERTY_CUSTOMERACCOUNTS, modifier);
   }

   public AccountSet getCustomerAccounts()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Bank) this.getCurrentMatch()).getCustomerAccounts();
      }
      return null;
   }

   
   //==========================================================================
   
   public boolean validateLogin(int accountID, String username, String password)
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Bank) getCurrentMatch()).validateLogin(accountID, username, password);
      }
      return false;
   }

   
   //==========================================================================
   
   public org.sdmlib.openbank.Account findAccountByID(int accountID)
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Bank) getCurrentMatch()).findAccountByID(accountID);
      }
      return null;
   }

   
   //==========================================================================
   
   public org.sdmlib.openbank.User findUserByID(String userID)
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Bank) getCurrentMatch()).findUserByID(userID);
      }
      return null;
   }

   
   //==========================================================================
   
   public boolean confirmTransaction(int toAcctID, int fromAcctID, BigInteger dollarValue, BigInteger decimalValue)
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Bank) getCurrentMatch()).confirmTransaction(toAcctID, fromAcctID, dollarValue, decimalValue);
      }
      return false;
   }

   public UserPO createAdminUsersPO()
   {
      UserPO result = new UserPO(new User[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Bank.PROPERTY_ADMINUSERS, result);
      
      return result;
   }

   public UserPO createAdminUsersPO(String modifier)
   {
      UserPO result = new UserPO(new User[]{});
      
      result.setModifier(modifier);
      super.hasLink(Bank.PROPERTY_ADMINUSERS, result);
      
      return result;
   }

   public BankPO createAdminUsersLink(UserPO tgt)
   {
      return hasLinkConstraint(tgt, Bank.PROPERTY_ADMINUSERS);
   }

   public BankPO createAdminUsersLink(UserPO tgt, String modifier)
   {
      return hasLinkConstraint(tgt, Bank.PROPERTY_ADMINUSERS, modifier);
   }

   public UserSet getAdminUsers()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Bank) this.getCurrentMatch()).getAdminUsers();
      }
      return null;
   }

   public AccountPO createAdminAccountsPO()
   {
      AccountPO result = new AccountPO(new Account[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Bank.PROPERTY_ADMINACCOUNTS, result);
      
      return result;
   }

   public AccountPO createAdminAccountsPO(String modifier)
   {
      AccountPO result = new AccountPO(new Account[]{});
      
      result.setModifier(modifier);
      super.hasLink(Bank.PROPERTY_ADMINACCOUNTS, result);
      
      return result;
   }

   public BankPO createAdminAccountsLink(AccountPO tgt)
   {
      return hasLinkConstraint(tgt, Bank.PROPERTY_ADMINACCOUNTS);
   }

   public BankPO createAdminAccountsLink(AccountPO tgt, String modifier)
   {
      return hasLinkConstraint(tgt, Bank.PROPERTY_ADMINACCOUNTS, modifier);
   }

   public AccountSet getAdminAccounts()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Bank) this.getCurrentMatch()).getAdminAccounts();
      }
      return null;
   }

   
   //==========================================================================
   
   public boolean confirmTransaction(int toAcctID, int fromAcctID, Integer dollarValue, Integer decimalValue)
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Bank) getCurrentMatch()).confirmTransaction(toAcctID, fromAcctID, dollarValue, decimalValue);
      }
      return false;
   }

   public FeeValuePO createFeeValuePO()
   {
      FeeValuePO result = new FeeValuePO(new FeeValue[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Bank.PROPERTY_FEEVALUE, result);
      
      return result;
   }

   public FeeValuePO createFeeValuePO(String modifier)
   {
      FeeValuePO result = new FeeValuePO(new FeeValue[]{});
      
      result.setModifier(modifier);
      super.hasLink(Bank.PROPERTY_FEEVALUE, result);
      
      return result;
   }

   public BankPO createFeeValueLink(FeeValuePO tgt)
   {
      return hasLinkConstraint(tgt, Bank.PROPERTY_FEEVALUE);
   }

   public BankPO createFeeValueLink(FeeValuePO tgt, String modifier)
   {
      return hasLinkConstraint(tgt, Bank.PROPERTY_FEEVALUE, modifier);
   }

   public FeeValueSet getFeeValue()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Bank) this.getCurrentMatch()).getFeeValue();
      }
      return null;
   }

   
   //==========================================================================
   
   public String Login(String username, String password)
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Bank) getCurrentMatch()).Login(username, password);
      }
      return null;
   }

   
   //==========================================================================
   
   public java.math.BigInteger withDrawFunds(int accountNum, BigInteger amount, StringBuilder msg)
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Bank) getCurrentMatch()).withDrawFunds(accountNum, amount, msg);
      }
      return null;
   }

   
   //==========================================================================
   
   public java.math.BigInteger depositFunds(int accountNum, BigInteger amount, StringBuilder msg)
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Bank) getCurrentMatch()).depositFunds(accountNum, amount, msg);
      }
      return null;
   }

   
   //==========================================================================
   
   public String updateUserInfo(String userID, String fieldName, String fieldValue)
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Bank) getCurrentMatch()).updateUserInfo(userID, fieldName, fieldValue);
      }
      return null;
   }

}
