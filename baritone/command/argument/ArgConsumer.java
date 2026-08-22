package baritone.command.argument;

import baritone.Baritone;
import baritone.api.IBaritone;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.argument.ICommandArgument;
import baritone.api.command.datatypes.IDatatype;
import baritone.api.command.datatypes.IDatatypeContext;
import baritone.api.command.datatypes.IDatatypeFor;
import baritone.api.command.datatypes.IDatatypePost;
import baritone.api.command.exception.CommandException;
import baritone.api.command.exception.CommandInvalidTypeException;
import baritone.api.command.exception.CommandNotEnoughArgumentsException;
import baritone.api.command.exception.CommandTooManyArgumentsException;
import baritone.api.command.manager.ICommandManager;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class ArgConsumer implements IArgConsumer {
   final ICommandManager a;
   private final Context a;
   private final LinkedList<ICommandArgument> a;
   private final Deque<ICommandArgument> a;

   private ArgConsumer(ICommandManager var1, Deque<ICommandArgument> var2, Deque<ICommandArgument> var3) {
      this.a = var1;
      this.a = new Context();
      this.a = new LinkedList(var2);
      this.a = new LinkedList(var3);
   }

   public ArgConsumer(ICommandManager var1, List<ICommandArgument> var2) {
      this(var1, new LinkedList(var2), new LinkedList());
   }

   public LinkedList<ICommandArgument> getArgs() {
      return this.a;
   }

   public Deque<ICommandArgument> getConsumed() {
      return this.a;
   }

   public boolean has(int var1) {
      return this.a.size() >= var1;
   }

   public boolean hasAny() {
      return this.has(1);
   }

   public boolean hasAtMost(int var1) {
      return this.a.size() <= var1;
   }

   public boolean hasAtMostOne() {
      return this.hasAtMost(1);
   }

   public boolean hasExactly(int var1) {
      return this.a.size() == var1;
   }

   public boolean hasExactlyOne() {
      return this.hasExactly(1);
   }

   public ICommandArgument peek(int var1) {
      this.requireMin(var1 + 1);
      return (ICommandArgument)this.a.get(var1);
   }

   public ICommandArgument peek() {
      return this.peek(0);
   }

   public boolean is(Class<?> var1, int var2) {
      return this.peek(var2).is(var1);
   }

   public boolean is(Class<?> var1) {
      return this.is(var1, 0);
   }

   public String peekString(int var1) {
      return this.peek(var1).getValue();
   }

   public String peekString() {
      return this.peekString(0);
   }

   public <E extends Enum<?>> E peekEnum(Class<E> var1, int var2) {
      return (E)this.peek(var2).getEnum(var1);
   }

   public <E extends Enum<?>> E peekEnum(Class<E> var1) {
      return (E)this.peekEnum(var1, 0);
   }

   public <E extends Enum<?>> E peekEnumOrNull(Class<E> var1, int var2) {
      try {
         return (E)this.peekEnum(var1, var2);
      } catch (CommandInvalidTypeException var3) {
         return null;
      }
   }

   public <E extends Enum<?>> E peekEnumOrNull(Class<E> var1) {
      return (E)this.peekEnumOrNull(var1, 0);
   }

   public <T> T peekAs(Class<T> var1, int var2) {
      return (T)this.peek(var2).getAs(var1);
   }

   public <T> T peekAs(Class<T> var1) {
      return (T)this.peekAs(var1, 0);
   }

   public <T> T peekAsOrDefault(Class<T> var1, T var2, int var3) {
      try {
         return (T)this.peekAs(var1, var3);
      } catch (CommandInvalidTypeException var4) {
         return var2;
      }
   }

   public <T> T peekAsOrDefault(Class<T> var1, T var2) {
      return (T)this.peekAsOrDefault(var1, var2, 0);
   }

   public <T> T peekAsOrNull(Class<T> var1, int var2) {
      return (T)this.peekAsOrDefault(var1, (Object)null, var2);
   }

   public <T> T peekAsOrNull(Class<T> var1) {
      return (T)this.peekAsOrNull(var1, 0);
   }

   public <T> T peekDatatype(IDatatypeFor<T> var1) {
      return (T)this.a().getDatatypeFor(var1);
   }

   public <T, O> T peekDatatype(IDatatypePost<T, O> var1) {
      return (T)this.peekDatatype(var1, (Object)null);
   }

   public <T, O> T peekDatatype(IDatatypePost<T, O> var1, O var2) {
      return (T)this.a().getDatatypePost(var1, var2);
   }

   public <T> T peekDatatypeOrNull(IDatatypeFor<T> var1) {
      return (T)this.a().getDatatypeForOrNull(var1);
   }

   public <T, O> T peekDatatypeOrNull(IDatatypePost<T, O> var1) {
      return (T)this.a().getDatatypePostOrNull(var1, (Object)null);
   }

   public <T, O, D extends IDatatypePost<T, O>> T peekDatatypePost(D var1, O var2) {
      return (T)this.a().getDatatypePost(var1, var2);
   }

   public <T, O, D extends IDatatypePost<T, O>> T peekDatatypePostOrDefault(D var1, O var2, T var3) {
      return (T)this.a().getDatatypePostOrDefault(var1, var2, var3);
   }

   public <T, O, D extends IDatatypePost<T, O>> T peekDatatypePostOrNull(D var1, O var2) {
      return (T)this.peekDatatypePostOrDefault(var1, var2, (Object)null);
   }

   public <T, D extends IDatatypeFor<T>> T peekDatatypeFor(Class<D> var1) {
      return (T)this.a().peekDatatypeFor(var1);
   }

   public <T, D extends IDatatypeFor<T>> T peekDatatypeForOrDefault(Class<D> var1, T var2) {
      return (T)this.a().peekDatatypeForOrDefault(var1, var2);
   }

   public <T, D extends IDatatypeFor<T>> T peekDatatypeForOrNull(Class<D> var1) {
      return (T)this.peekDatatypeForOrDefault(var1, (Object)null);
   }

   public ICommandArgument get() {
      this.requireMin(1);
      ICommandArgument var1 = (ICommandArgument)this.a.removeFirst();
      this.a.add(var1);
      return var1;
   }

   public String getString() {
      return this.get().getValue();
   }

   public <E extends Enum<?>> E getEnum(Class<E> var1) {
      return (E)this.get().getEnum(var1);
   }

   public <E extends Enum<?>> E getEnumOrDefault(Class<E> var1, E var2) {
      try {
         this.peekEnum(var1);
         return (E)this.getEnum(var1);
      } catch (CommandInvalidTypeException var3) {
         return (E)var2;
      }
   }

   public <E extends Enum<?>> E getEnumOrNull(Class<E> var1) {
      return (E)this.getEnumOrDefault(var1, (Enum)null);
   }

   public <T> T getAs(Class<T> var1) {
      return (T)this.get().getAs(var1);
   }

   public <T> T getAsOrDefault(Class<T> var1, T var2) {
      try {
         Object var4 = this.peek().getAs(var1);
         this.get();
         return (T)var4;
      } catch (CommandInvalidTypeException var3) {
         return var2;
      }
   }

   public <T> T getAsOrNull(Class<T> var1) {
      return (T)this.getAsOrDefault(var1, (Object)null);
   }

   public <T, O, D extends IDatatypePost<T, O>> T getDatatypePost(D var1, O var2) {
      try {
         return (T)var1.apply(this.a, var2);
      } catch (Exception var3) {
         if ((Boolean)Baritone.a().verboseCommandExceptions.value) {
            var3.printStackTrace();
         }

         throw new CommandInvalidTypeException(this.hasAny() ? this.peek() : this.consumed(), var1.getClass().getSimpleName(), var3);
      }
   }

   public <T, O, D extends IDatatypePost<T, O>> T getDatatypePostOrDefault(D var1, O var2, T var3) {
      ArrayList var4 = new ArrayList(this.a);
      ArrayList var5 = new ArrayList(this.a);

      try {
         return (T)this.getDatatypePost(var1, var2);
      } catch (Exception var6) {
         this.a.clear();
         this.a.addAll(var4);
         this.a.clear();
         this.a.addAll(var5);
         return var3;
      }
   }

   public <T, O, D extends IDatatypePost<T, O>> T getDatatypePostOrNull(D var1, O var2) {
      return (T)this.getDatatypePostOrDefault(var1, var2, (Object)null);
   }

   public <T, D extends IDatatypeFor<T>> T getDatatypeFor(D var1) {
      try {
         return (T)var1.get(this.a);
      } catch (Exception var3) {
         if ((Boolean)Baritone.a().verboseCommandExceptions.value) {
            var3.printStackTrace();
         }

         throw new CommandInvalidTypeException(this.hasAny() ? this.peek() : this.consumed(), var1.getClass().getSimpleName(), var3);
      }
   }

   public <T, D extends IDatatypeFor<T>> T getDatatypeForOrDefault(D var1, T var2) {
      ArrayList var3 = new ArrayList(this.a);
      ArrayList var4 = new ArrayList(this.a);

      try {
         return (T)this.getDatatypeFor(var1);
      } catch (Exception var5) {
         this.a.clear();
         this.a.addAll(var3);
         this.a.clear();
         this.a.addAll(var4);
         return var2;
      }
   }

   public <T, D extends IDatatypeFor<T>> T getDatatypeForOrNull(D var1) {
      return (T)this.getDatatypeForOrDefault(var1, (Object)null);
   }

   public <T extends IDatatype> Stream<String> tabCompleteDatatype(T var1) {
      try {
         return var1.tabComplete(this.a);
      } catch (CommandException var2) {
      } catch (Exception var3) {
         var3.printStackTrace();
      }

      return Stream.empty();
   }

   public String rawRest() {
      return this.a.size() > 0 ? ((ICommandArgument)this.a.getFirst()).getRawRest() : "";
   }

   public void requireMin(int var1) {
      if (this.a.size() < var1) {
         throw new CommandNotEnoughArgumentsException(var1 + this.a.size());
      }
   }

   public void requireMax(int var1) {
      if (this.a.size() > var1) {
         throw new CommandTooManyArgumentsException(var1 + this.a.size());
      }
   }

   public void requireExactly(int var1) {
      this.requireMin(var1);
      this.requireMax(var1);
   }

   public boolean hasConsumed() {
      return !this.a.isEmpty();
   }

   public ICommandArgument consumed() {
      return (ICommandArgument)(this.a.size() > 0 ? (ICommandArgument)this.a.getLast() : CommandArguments.a());
   }

   public String consumedString() {
      return this.consumed().getValue();
   }

   private ArgConsumer a() {
      return new ArgConsumer(this.a, this.a, this.a);
   }

   // $FF: synthetic method
   public IArgConsumer copy() {
      return this.a();
   }

   final class Context implements IDatatypeContext {
      public final IBaritone getBaritone() {
         return ArgConsumer.this.a.getBaritone();
      }
   }
}
