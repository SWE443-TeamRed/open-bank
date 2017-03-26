package org.sdmlib.openbank.util;

import org.sdmlib.models.pattern.PatternObject;
import org.sdmlib.openbank.User;
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
   
   public UserPO createUserIDCondition(String value)
   {
      new AttributeConstraint()
      .withAttrName(User.PROPERTY_USERID)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public UserPO createUserIDCondition(String lower, String upper)
   {
      new AttributeConstraint()
      .withAttrName(User.PROPERTY_USERID)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public UserPO createUserIDAssignment(String value)
   {
      new AttributeConstraint()
      .withAttrName(User.PROPERTY_USERID)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public String getUserID()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((User) getCurrentMatch()).getUserID();
      }
      return null;
   }
   
   public UserPO withUserID(String value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((User) getCurrentMatch()).setUserID(value);
      }
      return this;
   }
   
}
