package games.stendhal.server.trade;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;

import org.junit.BeforeClass;
import org.junit.Test;

import utilities.PlayerTestHelper;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.assertThat;

public class OfferTest {
	
	@BeforeClass
	public static void setUp() {
		MockStendlRPWorld.get();
	}

	@Test
	public void testOffer() throws Exception {
		Item item = SingletonRepository.getEntityManager().getItem("money");
		Integer price = Integer.valueOf(1);
		String offererName = "trader";
		Player george = PlayerTestHelper.createPlayer(offererName);
		Offer o = new Offer(item, price, george );
		assertThat(o.getOfferer().getName(), is(offererName));
		assertThat(o.getInt("price"), is(price.intValue()));
		assertThat((Item) o.getSlot("item").getFirst(), is(item));
		Offer offerFromRPObject = new Offer(o);
		assertThat(offerFromRPObject.getPrice(), is(price));
		assertThat(offerFromRPObject.getOfferer().getName(), is(offererName));
		assertThat(offerFromRPObject.getItem(), is(item));
	}
}

