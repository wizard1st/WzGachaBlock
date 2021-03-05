package wz.mod.gacha.block;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import wz.mod.gacha.ContentItems;
import wz.mod.gacha.WzUtils;

public class GachaBlock extends FacingBlock {

	private static Ingredient ACCEPT_COIN = Ingredient.ofStacks(new ItemStack(ContentItems.KAK_COIN),
			new ItemStack(ContentItems.LOW_COIN), new ItemStack(ContentItems.MID_COIN),
			new ItemStack(ContentItems.HIGH_COIN), new ItemStack(ContentItems.MAX_COIN));
	private static final VoxelShape SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 30.0D, 16.0D);

	private static List<ItemStack> STACK_KAK, STACK_LOW, STACK_MID, STACK_HIGH, STACK_MAX;

	public GachaBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
	}

	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}

	public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
		// world.setBlockState(pos.up(), (BlockState) state.with(HALF,
		// DoubleBlockHalf.UPPER), 3);
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
			BlockHitResult hit) {
		ItemStack item = player.getMainHandStack();
		if (!world.isClient) {

			if (ACCEPT_COIN.test(item)) {
				if (!player.isCreative())
					item.decrement(1);

				if (item.getItem() == ContentItems.KAK_COIN) {
					int rand = world.random.nextInt(STACK_KAK.size());
					world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), STACK_KAK.get(rand)));
				} else if (item.getItem() == ContentItems.LOW_COIN) {
					int rand = world.random.nextInt(STACK_LOW.size());
					world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), STACK_LOW.get(rand)));
				} else if (item.getItem() == ContentItems.MID_COIN) {
					int rand = world.random.nextInt(STACK_MID.size());
					world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), STACK_MID.get(rand)));
				} else if (item.getItem() == ContentItems.HIGH_COIN) {
					int rand = world.random.nextInt(STACK_HIGH.size());
					world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), STACK_HIGH.get(rand)));
				} else if (item.getItem() == ContentItems.MAX_COIN) {
					int rand = world.random.nextInt(STACK_MAX.size());
					world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), STACK_MAX.get(rand)));
				}
				world.playSound(player, pos, SoundEvents.BLOCK_DISPENSER_DISPENSE, SoundCategory.BLOCKS, 1.0f, 1.0f);
			}
			
		}

		return ActionResult.SUCCESS;
	}

	static {
		STACK_KAK = WzUtils.stackFromFile(WzUtils.GACHA_FILE_NAME[0]);
		STACK_LOW = WzUtils.stackFromFile(WzUtils.GACHA_FILE_NAME[1]);
		STACK_MID = WzUtils.stackFromFile(WzUtils.GACHA_FILE_NAME[2]);
		STACK_HIGH = WzUtils.stackFromFile(WzUtils.GACHA_FILE_NAME[3]);
		STACK_MAX = WzUtils.stackFromFile(WzUtils.GACHA_FILE_NAME[4]);
	}
}
