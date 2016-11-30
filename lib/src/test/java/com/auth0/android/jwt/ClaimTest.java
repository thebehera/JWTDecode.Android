package com.auth0.android.jwt;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.hamcrest.collection.IsArrayWithSize;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class ClaimTest {

    private Gson gson;
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        gson = new Gson();
    }

    @Test
    public void shouldGetBooleanValue() throws Exception {
        JsonElement value = gson.toJsonTree(true);
        Claim claim = new Claim(value);

        assertThat(claim.asBoolean(), is(notNullValue()));
        assertThat(claim.asBoolean(), is(true));
    }

    @Test
    public void shouldThrowIfBooleanNotPrimitiveValue() throws Exception {
        JsonElement value = gson.toJsonTree(new Object());
        Claim claim = new Claim(value);

        exception.expect(DecodeException.class);
        claim.asBoolean();
    }

    @Test
    public void shouldGetIntValue() throws Exception {
        JsonElement value = gson.toJsonTree(123);
        Claim claim = new Claim(value);

        assertThat(claim.asInt(), is(notNullValue()));
        assertThat(claim.asInt(), is(123));
    }

    @Test
    public void shouldThrowIfIntNotPrimitiveValue() throws Exception {
        JsonElement value = gson.toJsonTree(new Object());
        Claim claim = new Claim(value);

        exception.expect(DecodeException.class);
        claim.asInt();
    }

    @Test
    public void shouldGetDoubleValue() throws Exception {
        JsonElement value = gson.toJsonTree(1.5);
        Claim claim = new Claim(value);

        assertThat(claim.asDouble(), is(notNullValue()));
        assertThat(claim.asDouble(), is(1.5));
    }

    @Test
    public void shouldThrowIfDoubleNotPrimitiveValue() throws Exception {
        JsonElement value = gson.toJsonTree(new Object());
        Claim claim = new Claim(value);

        exception.expect(DecodeException.class);
        claim.asDouble();
    }

    @Test
    public void shouldGetDateValue() throws Exception {
        JsonElement value = gson.toJsonTree("1476824844");
        Claim claim = new Claim(value);

        assertThat(claim.asDate(), is(notNullValue()));
        assertThat(claim.asDate(), is(new Date(1476824844L * 1000)));
    }

    @Test
    public void shouldThrowIfDateNotPrimitiveValue() throws Exception {
        JsonElement value = gson.toJsonTree(new Object());
        Claim claim = new Claim(value);

        exception.expect(DecodeException.class);
        claim.asDate();
    }

    @Test
    public void shouldGetStringValue() throws Exception {
        JsonElement value = gson.toJsonTree("string");
        Claim claim = new Claim(value);

        assertThat(claim.asString(), is(notNullValue()));
        assertThat(claim.asString(), is("string"));
    }

    @Test
    public void shouldThrowIfStringNotPrimitiveValue() throws Exception {
        JsonElement value = gson.toJsonTree(new Object());
        Claim claim = new Claim(value);

        exception.expect(DecodeException.class);
        claim.asString();
    }

    @Test
    public void shouldGetArrayValueOfCustomClass() throws Exception {
        JsonElement value = gson.toJsonTree(new UserPojo[]{new UserPojo("George", 1), new UserPojo("Mark", 2)});
        Claim claim = new Claim(value);

        assertThat(claim.asArray(UserPojo.class), is(notNullValue()));
        assertThat(claim.asArray(UserPojo.class), is(arrayContaining(new UserPojo("George", 1), new UserPojo("Mark", 2))));
    }

    @Test
    public void shouldGetArrayValue() throws Exception {
        JsonElement value = gson.toJsonTree(new String[]{"string1", "string2"});
        Claim claim = new Claim(value);

        assertThat(claim.asArray(String.class), is(notNullValue()));
        assertThat(claim.asArray(String.class), is(arrayContaining("string1", "string2")));
    }

    @Test
    public void shouldGetEmptyArrayIfNullValue() throws Exception {
        JsonElement value = gson.toJsonTree(null);
        Claim claim = new Claim(value);

        assertThat(claim.asArray(String.class), is(notNullValue()));
        assertThat(claim.asArray(String.class), is(IsArrayWithSize.<String>emptyArray()));
    }

    @Test
    public void shouldGetEmptyArrayIfNonArrayValue() throws Exception {
        JsonElement value = gson.toJsonTree(1);
        Claim claim = new Claim(value);

        assertThat(claim.asArray(String.class), is(notNullValue()));
        assertThat(claim.asArray(String.class), is(IsArrayWithSize.<String>emptyArray()));
    }

    @Test
    public void shouldThrowIfArrayClassMismatch() throws Exception {
        JsonElement value = gson.toJsonTree(new String[]{"keys", "values"});
        Claim claim = new Claim(value);

        exception.expect(DecodeException.class);
        claim.asArray(UserPojo.class);
    }

    @Test
    public void shouldGetListValueOfCustomClass() throws Exception {
        JsonElement value = gson.toJsonTree(Arrays.asList(new UserPojo("George", 1), new UserPojo("Mark", 2)));
        Claim claim = new Claim(value);

        assertThat(claim.asList(UserPojo.class), is(notNullValue()));
        assertThat(claim.asList(UserPojo.class), is(hasItems(new UserPojo("George", 1), new UserPojo("Mark", 2))));
    }

    @Test
    public void shouldGetListValue() throws Exception {
        JsonElement value = gson.toJsonTree(Arrays.asList("string1", "string2"));
        Claim claim = new Claim(value);

        assertThat(claim.asList(String.class), is(notNullValue()));
        assertThat(claim.asList(String.class), is(hasItems("string1", "string2")));
    }

    @Test
    public void shouldGetEmptyListIfNullValue() throws Exception {
        JsonElement value = gson.toJsonTree(null);
        Claim claim = new Claim(value);

        assertThat(claim.asList(String.class), is(notNullValue()));
        assertThat(claim.asList(String.class), is(IsEmptyCollection.emptyCollectionOf(String.class)));
    }

    @Test
    public void shouldGetEmptyListIfNonArrayValue() throws Exception {
        JsonElement value = gson.toJsonTree(1);
        Claim claim = new Claim(value);

        assertThat(claim.asList(String.class), is(notNullValue()));
        assertThat(claim.asList(String.class), is(IsEmptyCollection.emptyCollectionOf(String.class)));
    }

    @Test
    public void shouldThrowIfListClassMismatch() throws Exception {
        JsonElement value = gson.toJsonTree(new String[]{"keys", "values"});
        Claim claim = new Claim(value);

        exception.expect(DecodeException.class);
        claim.asList(UserPojo.class);
    }
}