/*
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

package com.facebook.litho;

import static android.support.v4.view.ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_AUTO;
import static com.facebook.litho.LayoutState.createAndMeasureTreeForComponent;
import static com.facebook.litho.SizeSpec.EXACTLY;
import static com.facebook.litho.SizeSpec.UNSPECIFIED;
import static com.facebook.litho.SizeSpec.makeSizeSpec;
import static com.facebook.litho.it.R.drawable.background_with_padding;
import static com.facebook.litho.it.R.drawable.background_without_padding;
import static com.facebook.yoga.YogaAlign.STRETCH;
import static com.facebook.yoga.YogaDirection.INHERIT;
import static com.facebook.yoga.YogaDirection.RTL;
import static com.facebook.yoga.YogaEdge.ALL;
import static com.facebook.yoga.YogaPositionType.ABSOLUTE;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.powermock.reflect.Whitebox.getInternalState;
import static org.robolectric.RuntimeEnvironment.application;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import com.facebook.litho.testing.testrunner.ComponentsTestRunner;
import com.facebook.litho.widget.Text;
import com.facebook.yoga.YogaAlign;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.reflect.Whitebox;
import org.robolectric.RuntimeEnvironment;

@RunWith(ComponentsTestRunner.class)
public class InternalNodeTest {
  private static final int LIFECYCLE_TEST_ID = 1;

  private static class TestComponent extends Component {

    protected TestComponent() {
      super("TestComponent");
    }

    @Override
    int getTypeId() {
      return LIFECYCLE_TEST_ID;
    }
  }

  private static InternalNode acquireInternalNode() {
    final ComponentContext context = new ComponentContext(RuntimeEnvironment.application);
    return createAndMeasureTreeForComponent(
        context,
        Column.create(context).build(),
        makeSizeSpec(0, UNSPECIFIED),
        makeSizeSpec(0, UNSPECIFIED));
  }

  @Test
  public void testLayoutDirectionFlag() {
    final InternalNode node = acquireInternalNode();
    node.layoutDirection(INHERIT);
    assertThat(isFlagSet(node, "PFLAG_LAYOUT_DIRECTION_IS_SET")).isTrue();
    clearFlag(node, "PFLAG_LAYOUT_DIRECTION_IS_SET");
    assertEmptyFlags(node);
  }

  @Test
  public void testAlignSelfFlag() {
    final InternalNode node = acquireInternalNode();
    node.alignSelf(STRETCH);
    assertThat(isFlagSet(node, "PFLAG_ALIGN_SELF_IS_SET")).isTrue();
    clearFlag(node, "PFLAG_ALIGN_SELF_IS_SET");
    assertEmptyFlags(node);
  }

  @Test
  public void testPositionTypeFlag() {
    final InternalNode node = acquireInternalNode();
    node.positionType(ABSOLUTE);
    assertThat(isFlagSet(node, "PFLAG_POSITION_TYPE_IS_SET")).isTrue();
    clearFlag(node, "PFLAG_POSITION_TYPE_IS_SET");
    assertEmptyFlags(node);
  }

  @Test
  public void testFlexFlag() {
    final InternalNode node = acquireInternalNode();
    node.flex(1.5f);
    assertThat(isFlagSet(node, "PFLAG_FLEX_IS_SET")).isTrue();
    clearFlag(node, "PFLAG_FLEX_IS_SET");
    assertEmptyFlags(node);
  }

  @Test
  public void testFlexGrowFlag() {
    final InternalNode node = acquireInternalNode();
    node.flexGrow(1.5f);
    assertThat(isFlagSet(node, "PFLAG_FLEX_GROW_IS_SET")).isTrue();
    clearFlag(node, "PFLAG_FLEX_GROW_IS_SET");
    assertEmptyFlags(node);
  }

  @Test
  public void testFlexShrinkFlag() {
    final InternalNode node = acquireInternalNode();
    node.flexShrink(1.5f);
    assertThat(isFlagSet(node, "PFLAG_FLEX_SHRINK_IS_SET")).isTrue();
    clearFlag(node, "PFLAG_FLEX_SHRINK_IS_SET");
    assertEmptyFlags(node);
  }

  @Test
  public void testFlexBasisFlag() {
    final InternalNode node = acquireInternalNode();
    node.flexBasisPx(1);
    assertThat(isFlagSet(node, "PFLAG_FLEX_BASIS_IS_SET")).isTrue();
    clearFlag(node, "PFLAG_FLEX_BASIS_IS_SET");
    assertEmptyFlags(node);
  }

  @Test
  public void testImportantForAccessibilityFlag() {
    final InternalNode node = acquireInternalNode();
    node.importantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_AUTO);
    assertThat(isFlagSet(node, "PFLAG_IMPORTANT_FOR_ACCESSIBILITY_IS_SET")).isTrue();
    clearFlag(node, "PFLAG_IMPORTANT_FOR_ACCESSIBILITY_IS_SET");
    assertEmptyFlags(node);
  }

  @Test
  public void testDuplicateParentStateFlag() {
    final InternalNode node = acquireInternalNode();
    node.duplicateParentState(false);
    assertThat(isFlagSet(node, "PFLAG_DUPLICATE_PARENT_STATE_IS_SET")).isTrue();
    clearFlag(node, "PFLAG_DUPLICATE_PARENT_STATE_IS_SET");
    assertEmptyFlags(node);
  }

  @Test
  public void testMarginFlag() {
    final InternalNode node = acquireInternalNode();
    node.marginPx(ALL, 3);
    assertThat(isFlagSet(node, "PFLAG_MARGIN_IS_SET")).isTrue();
    clearFlag(node, "PFLAG_MARGIN_IS_SET");
    assertEmptyFlags(node);
  }

  @Test
  public void testPaddingFlag() {
    final InternalNode node = acquireInternalNode();
    node.paddingPx(ALL, 3);
    assertThat(isFlagSet(node, "PFLAG_PADDING_IS_SET")).isTrue();
    clearFlag(node, "PFLAG_PADDING_IS_SET");
    assertEmptyFlags(node);
  }

  @Test
  public void testPositionFlag() {
    final InternalNode node = acquireInternalNode();
    node.positionPx(ALL, 3);
    assertThat(isFlagSet(node, "PFLAG_POSITION_IS_SET")).isTrue();
    clearFlag(node, "PFLAG_POSITION_IS_SET");
    assertEmptyFlags(node);
  }

  @Test
  public void testWidthFlag() {
    final InternalNode node = acquireInternalNode();
    node.widthPx(4);
    assertThat(isFlagSet(node, "PFLAG_WIDTH_IS_SET")).isTrue();
    clearFlag(node, "PFLAG_WIDTH_IS_SET");
    assertEmptyFlags(node);
  }

  @Test
  public void testMinWidthFlag() {
    final InternalNode node = acquireInternalNode();
    node.minWidthPx(4);
    assertThat(isFlagSet(node, "PFLAG_MIN_WIDTH_IS_SET")).isTrue();
    clearFlag(node, "PFLAG_MIN_WIDTH_IS_SET");
    assertEmptyFlags(node);
  }

  @Test
  public void testMaxWidthFlag() {
    final InternalNode node = acquireInternalNode();
    node.maxWidthPx(4);
    assertThat(isFlagSet(node, "PFLAG_MAX_WIDTH_IS_SET")).isTrue();
    clearFlag(node, "PFLAG_MAX_WIDTH_IS_SET");
    assertEmptyFlags(node);
  }

  @Test
  public void testHeightFlag() {
    final InternalNode node = acquireInternalNode();
    node.heightPx(4);
    assertThat(isFlagSet(node, "PFLAG_HEIGHT_IS_SET")).isTrue();
    clearFlag(node, "PFLAG_HEIGHT_IS_SET");
    assertEmptyFlags(node);
  }

  @Test
  public void testMinHeightFlag() {
    final InternalNode node = acquireInternalNode();
    node.minHeightPx(4);
    assertThat(isFlagSet(node, "PFLAG_MIN_HEIGHT_IS_SET")).isTrue();
    clearFlag(node, "PFLAG_MIN_HEIGHT_IS_SET");
    assertEmptyFlags(node);
  }

  @Test
  public void testMaxHeightFlag() {
    final InternalNode node = acquireInternalNode();
    node.maxHeightPx(4);
    assertThat(isFlagSet(node, "PFLAG_MAX_HEIGHT_IS_SET")).isTrue();
    clearFlag(node, "PFLAG_MAX_HEIGHT_IS_SET");
    assertEmptyFlags(node);
  }

  @Test
  public void testBackgroundFlag() {
    final InternalNode node = acquireInternalNode();
    node.backgroundColor(0xFFFF0000);
    assertThat(isFlagSet(node, "PFLAG_BACKGROUND_IS_SET")).isTrue();
    clearFlag(node, "PFLAG_BACKGROUND_IS_SET");
    assertEmptyFlags(node);
  }

  @Test
  public void testForegroundFlag() {
    final InternalNode node = acquireInternalNode();
    node.foregroundColor(0xFFFF0000);
    assertThat(isFlagSet(node, "PFLAG_FOREGROUND_IS_SET")).isTrue();
    clearFlag(node, "PFLAG_FOREGROUND_IS_SET");
    assertEmptyFlags(node);
  }

  @Test
  public void testAspectRatioFlag() {
    final InternalNode node = acquireInternalNode();
    node.aspectRatio(1);
    assertThat(isFlagSet(node, "PFLAG_ASPECT_RATIO_IS_SET")).isTrue();
    clearFlag(node, "PFLAG_ASPECT_RATIO_IS_SET");
    assertEmptyFlags(node);
  }

  @Test
  public void testTransitionKeyFlag() {
    final InternalNode node = acquireInternalNode();
    node.transitionKey("key");
    assertThat(isFlagSet(node, "PFLAG_TRANSITION_KEY_IS_SET")).isTrue();
    clearFlag(node, "PFLAG_TRANSITION_KEY_IS_SET");
    assertEmptyFlags(node);
  }

  @Test
  public void setNestedTreeDoesntTransferLayoutDirectionIfExplicitlySetOnNestedNode() {
    InternalNode holderNode = acquireInternalNode();
    InternalNode nestedTree = acquireInternalNode();

    nestedTree.layoutDirection(RTL);
    holderNode.calculateLayout();
    holderNode.setNestedTree(nestedTree);

    assertThat(isFlagSet(holderNode, "PFLAG_LAYOUT_DIRECTION_IS_SET")).isFalse();
    assertThat(holderNode.getStyleDirection()).isEqualTo(INHERIT);
    assertThat(nestedTree.getStyleDirection()).isEqualTo(RTL);
  }

  @Test
  public void testCopyIntoTrasferLayoutDirectionIfNotSetOnTheHolderOrOnTheNestedTree() {
    InternalNode holderNode = acquireInternalNode();
    InternalNode nestedTree = acquireInternalNode();

    holderNode.calculateLayout();
    holderNode.copyInto(nestedTree);

    assertThat(isFlagSet(holderNode, "PFLAG_LAYOUT_DIRECTION_IS_SET")).isFalse();
    assertThat(isFlagSet(nestedTree, "PFLAG_LAYOUT_DIRECTION_IS_SET")).isTrue();
  }

  @Test
  public void testCopyIntoNestedTreeTransferLayoutDirectionIfExplicitlySetOnHolderNode() {
    InternalNode holderNode = acquireInternalNode();
    InternalNode nestedTree = acquireInternalNode();

    holderNode.layoutDirection(RTL);
    holderNode.calculateLayout();
    holderNode.copyInto(nestedTree);

    assertThat(nestedTree.getStyleDirection()).isEqualTo(RTL);
  }

  @Test
  public void testPaddingIsSetFromDrawable() {
    InternalNode node = acquireInternalNode();

    Drawable drawable = ContextCompat.getDrawable(node.getContext(), background_with_padding);
    node.background(drawable);

    assertThat(isFlagSet(node, "PFLAG_PADDING_IS_SET")).isTrue();
  }

  @Test
  public void testPaddingIsNotSetFromDrawable() {
    InternalNode node = acquireInternalNode();

    Drawable drawable = ContextCompat.getDrawable(node.getContext(), background_without_padding);
    node.background(drawable);

    assertThat(isFlagSet(node, "PFLAG_PADDING_IS_SET")).isFalse();
  }

  @Test
  public void testComponentCreateAndRetrieveCachedLayout() {
    final ComponentContext c = new ComponentContext(application);
    final int unspecifiedSizeSpec = makeSizeSpec(0, UNSPECIFIED);
    final int exactSizeSpec = makeSizeSpec(50, EXACTLY);
    final Component textComponent = Text.create(c)
        .textSizePx(16)
        .text("test")
        .build();
    final Size textSize = new Size();
    textComponent.measure(
        c,
        exactSizeSpec,
        unspecifiedSizeSpec,
        textSize);

    assertThat(textComponent.hasCachedLayout()).isTrue();
    InternalNode cachedLayout = textComponent.getCachedLayout();
    assertThat(cachedLayout).isNotNull();
    assertThat(cachedLayout.getLastWidthSpec()).isEqualTo(exactSizeSpec);
    assertThat(cachedLayout.getLastHeightSpec()).isEqualTo(unspecifiedSizeSpec);

    textComponent.clearCachedLayout();
    assertThat(textComponent.hasCachedLayout()).isFalse();
  }

  @Test
  public void testContextSpecificComponentAssertionPasses() {
    InternalNode.assertContextSpecificStyleNotSet(acquireInternalNode());
  }

  @Test
  public void testContextSpecificComponentAssertionFailFormatting() {
    final Component testComponent = new TestComponent();
    InternalNode node = acquireInternalNode();
    node.alignSelf(YogaAlign.AUTO);
    node.flex(1f);
    node.appendComponent(testComponent);

    String error = "";
    try {
      InternalNode.assertContextSpecificStyleNotSet(node);
    } catch (IllegalStateException e) {
      error = e.getMessage();
    }

    assertTrue(
        "The error message contains the attributes set",
        error.contains("alignSelf, flex"));
  }

  private static boolean isFlagSet(InternalNode internalNode, String flagName) {
    long flagPosition = Whitebox.getInternalState(InternalNode.class, flagName);
    long flags = Whitebox.getInternalState(internalNode, "mPrivateFlags");

    return ((flags & flagPosition) != 0);
  }

  private static void clearFlag(InternalNode internalNode, String flagName) {
    long flagPosition = Whitebox.getInternalState(InternalNode.class, flagName);
    long flags = Whitebox.getInternalState(internalNode, "mPrivateFlags");
    flags &= ~flagPosition;
    Whitebox.setInternalState(internalNode, "mPrivateFlags", flags);
  }

  private static void assertEmptyFlags(InternalNode internalNode) {
    assertThat(((long) getInternalState(internalNode, "mPrivateFlags")) == 0).isTrue();
  }
}
