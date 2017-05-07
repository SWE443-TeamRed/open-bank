package com.app.swe443.openbankapp.Support;

import org.sdmlib.models.pattern.PatternObject;
import org.sdmlib.models.pattern.AttributeConstraint;
import org.sdmlib.models.pattern.Pattern;


public class UserPO extends PatternObject<UserPO, User>
{

    public UserSet allMatches()
   {
      this.setDoAllMatches(true);
      
      UserSet matches = new UserSet();

      while (this.getPattern().getHasMatch())
      {
         matches.add((User) this.getCurrentMatch());
         
         this.getPattern().findMatch();
      }
      
      return matches;
   }


   public UserPO(){
      newInstance(null);
   }

   public UserPO(User... hostGraphObject) {
      if(hostGraphObject==null || hostGraphObject.length<1){
         return ;
      }
      newInstance(null, hostGraphObject);
   }

   public UserPO(String modifier)
   {
      this.setModifier(modifier);
   }
   
   //==========================================================================
   
   public boolean openAccount(User p0)
   {
      if (this.getPattern().getHasMatch())
      {
         return ((User) getCurrentMatch()).openAccount(p0);
      }
      return false;
   }

   
   

   
   //==========================================================================
   
   public boolean logout()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((User) getCurrentMatch()).logout();
      }
      return false;
   }


   

   
   public UserPO createIsAdminCondition(boolean value)
   {
      new AttributeConstraint()
      .withAttrName(User.PROPERTY_ISADMIN)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public UserPO createIsAdminAssignment(boolean value)
   {
      new AttributeConstraint()
      .withAttrName(User.PROPERTY_ISADMIN)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public boolean getIsAdmin()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((User) getCurrentMatch()).isIsAdmin();
      }
      return false;
   }
   
   public UserPO withIsAdmin(boolean value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((User) getCurrentMatch()).setIsAdmin(value);
      }
      return this;
   }
   
   public UserPO createPasswordCondition(String value)
   {
      new AttributeConstraint()
      .withAttrName(User.PROPERTY_PASSWORD)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public UserPO createPasswordCondition(String lower, String upper)
   {
      new AttributeConstraint()
      .withAttrName(User.PROPERTY_PASSWORD)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public UserPO createPasswordAssignment(String value)
   {
      new AttributeConstraint()
      .withAttrName(User.PROPERTY_PASSWORD)
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
         return ((User) getCurrentMatch()).getPassword();
      }
      return null;
   }
   
   public UserPO withPassword(String value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((User) getCurrentMatch()).setPassword(value);
      }
      return this;
   }
   
   public UserPO createNameCondition(String value)
   {
      new AttributeConstraint()
      .withAttrName(User.PROPERTY_NAME)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public UserPO createNameCondition(String lower, String upper)
   {
      new AttributeConstraint()
      .withAttrName(User.PROPERTY_NAME)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public UserPO createNameAssignment(String value)
   {
      new AttributeConstraint()
      .withAttrName(User.PROPERTY_NAME)
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
         return ((User) getCurrentMatch()).getName();
      }
      return null;
   }
   
   public UserPO withName(String value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((User) getCurrentMatch()).setName(value);
      }
      return this;
   }
   
   public UserPO createEmailCondition(String value)
   {
      new AttributeConstraint()
      .withAttrName(User.PROPERTY_EMAIL)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public UserPO createEmailCondition(String lower, String upper)
   {
      new AttributeConstraint()
      .withAttrName(User.PROPERTY_EMAIL)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public UserPO createEmailAssignment(String value)
   {
      new AttributeConstraint()
      .withAttrName(User.PROPERTY_EMAIL)
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
         return ((User) getCurrentMatch()).getEmail();
      }
      return null;
   }
   
   public UserPO withEmail(String value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((User) getCurrentMatch()).setEmail(value);
      }
      return this;
   }
   
   public UserPO createLoggedInCondition(boolean value)
   {
      new AttributeConstraint()
      .withAttrName(User.PROPERTY_LOGGEDIN)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public UserPO createLoggedInAssignment(boolean value)
   {
      new AttributeConstraint()
      .withAttrName(User.PROPERTY_LOGGEDIN)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public boolean getLoggedIn()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((User) getCurrentMatch()).isLoggedIn();
      }
      return false;
   }
   
   public UserPO withLoggedIn(boolean value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((User) getCurrentMatch()).setLoggedIn(value);
      }
      return this;
   }
   
   public UserPO createPhoneCondition(int value)
   {
      new AttributeConstraint()
      .withAttrName(User.PROPERTY_PHONE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }

   /*
   public UserPO createPhoneCondition(int lower, int upper)
   {
      new AttributeConstraint()
      .withAttrName(User.PROPERTY_PHONE)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   */

   public UserPO createPhoneAssignment(int value)
   {
      new AttributeConstraint()
      .withAttrName(User.PROPERTY_PHONE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public String getPhone()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((User) getCurrentMatch()).getPhone();
      }
      return null;
   }
   
   public UserPO withPhone(String value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((User) getCurrentMatch()).setPhone(value);
      }
      return this;
   }
   
   public AccountPO createAccountPO()
   {
      AccountPO result = new AccountPO(new Account[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(User.PROPERTY_ACCOUNT, result);
      
      return result;
   }

   public AccountPO createAccountPO(String modifier)
   {
      AccountPO result = new AccountPO(new Account[]{});
      
      result.setModifier(modifier);
      super.hasLink(User.PROPERTY_ACCOUNT, result);
      
      return result;
   }

   public UserPO createAccountLink(AccountPO tgt)
   {
      return hasLinkConstraint(tgt, User.PROPERTY_ACCOUNT);
   }

   public UserPO createAccountLink(AccountPO tgt, String modifier)
   {
      return hasLinkConstraint(tgt, User.PROPERTY_ACCOUNT, modifier);
   }

   public AccountSet getAccount()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((User) this.getCurrentMatch()).getAccount();
      }
      return null;
   }

   public UserPO createPhoneCondition(String value)
   {
      new AttributeConstraint()
      .withAttrName(User.PROPERTY_PHONE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public UserPO createPhoneCondition(String lower, String upper)
   {
      new AttributeConstraint()
      .withAttrName(User.PROPERTY_PHONE)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public UserPO createPhoneAssignment(String value)
   {
      new AttributeConstraint()
      .withAttrName(User.PROPERTY_PHONE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public UserPO createUsernameCondition(String value)
   {
      new AttributeConstraint()
      .withAttrName(User.PROPERTY_USERNAME)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public UserPO createUsernameCondition(String lower, String upper)
   {
      new AttributeConstraint()
      .withAttrName(User.PROPERTY_USERNAME)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public UserPO createUsernameAssignment(String value)
   {
      new AttributeConstraint()
      .withAttrName(User.PROPERTY_USERNAME)
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
         return ((User) getCurrentMatch()).getUsername();
      }
      return null;
   }
   
   public UserPO withUsername(String value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((User) getCurrentMatch()).setUsername(value);
      }
      return this;
   }
   
   public BankPO createBankPO()
   {
      BankPO result = new BankPO(new Bank[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(User.PROPERTY_BANK, result);
      
      return result;
   }

   public BankPO createBankPO(String modifier)
   {
      BankPO result = new BankPO(new Bank[]{});
      
      result.setModifier(modifier);
      super.hasLink(User.PROPERTY_BANK, result);
      
      return result;
   }

   public UserPO createBankLink(BankPO tgt)
   {
      return hasLinkConstraint(tgt, User.PROPERTY_BANK);
   }

   public UserPO createBankLink(BankPO tgt, String modifier)
   {
      return hasLinkConstraint(tgt, User.PROPERTY_BANK, modifier);
   }

   public Bank getBank()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((User) this.getCurrentMatch()).getBank();
      }
      return null;
   }

   public BankPO createEmployingBankPO()
   {
      BankPO result = new BankPO(new Bank[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(User.PROPERTY_EMPLOYINGBANK, result);
      
      return result;
   }

   public BankPO createEmployingBankPO(String modifier)
   {
      BankPO result = new BankPO(new Bank[]{});
      
      result.setModifier(modifier);
      super.hasLink(User.PROPERTY_EMPLOYINGBANK, result);
      
      return result;
   }

   public UserPO createEmployingBankLink(BankPO tgt)
   {
      return hasLinkConstraint(tgt, User.PROPERTY_EMPLOYINGBANK);
   }

   public UserPO createEmployingBankLink(BankPO tgt, String modifier)
   {
      return hasLinkConstraint(tgt, User.PROPERTY_EMPLOYINGBANK, modifier);
   }

   public Bank getEmployingBank()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((User) this.getCurrentMatch()).getEmployingBank();
      }
      return null;
   }

}
