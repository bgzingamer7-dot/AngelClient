package io.github.solclient.client.ui.screen;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiResourcePackAvailable;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.ResourcePackListEntry;
import net.minecraft.client.resources.ResourcePackListEntryFound;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.util.EnumChatFormatting;

public class BetterResourcePackList extends GuiResourcePackAvailable {

    private GuiScreenResourcePacks parent;
    public BetterResourcePackList delegate;
    protected List<ResourcePackListEntry> entries;
    private List<ResourcePackListEntry> filteredEntriesCache;
    private String lastFilter = "";
    private Supplier<String> supplier;
    private File folder;
    private List<ResourcePackListEntry> allPacksFlat;

    public BetterResourcePackList(Minecraft mcIn, GuiScreenResourcePacks parent, int width, int height,
                                  ResourcePackRepository repository, Supplier<String> supplier) throws IOException {
        super(mcIn, width, height, null);
        this.supplier = supplier;
        this.folder = repository.getDirResourcepacks();
        this.parent = parent;
        this.entries = new ArrayList<>();
        this.allPacksFlat = new ArrayList<>();

        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    if (!new File(file, "pack.mcmeta").exists()) {
                        entries.add(new FolderResourcePackEntry(parent, this, file));
                    }
                }
            }
        }

        for (ResourcePackRepository.Entry entry : repository.getRepositoryEntriesAll()) {
            entries.add(new ResourcePackListEntryFound(parent, entry));
        }
        
        populate(allPacksFlat);
        this.filteredEntriesCache = new ArrayList<>(entries);
    }

    // --- MÉTODOS QUE ESTAVAM FALTANDO PARA O BUILD ---
    
    public int getHeight() {
        return this.height;
    }

    public Supplier<String> getSupplier() {
        return this.supplier;
    }

    public File getFolder() {
        return delegate != null ? delegate.getFolder() : folder;
    }
    
    // ------------------------------------------------

    @Override
    public List<ResourcePackListEntry> getList() {
        if (delegate != null) return delegate.getList();

        String currentFilter = supplier.get().toLowerCase();

        if (!currentFilter.equals(lastFilter)) {
            lastFilter = currentFilter;
            updateFilteredList();
        }

        return filteredEntriesCache;
    }

    public void updateFilteredList() {
        String filter = lastFilter;
        List<ResourcePackListEntry> source = filter.isEmpty() ? entries : allPacksFlat;

        this.filteredEntriesCache = source.stream().filter((entry) -> {
            String name = "";
            String description = "";

            if (entry instanceof ResourcePackListEntryFound) {
                ResourcePackRepository.Entry repoEntry = ((ResourcePackListEntryFound) entry).func_148318_i();
                name = repoEntry.getResourcePackName();
                description = repoEntry.getTexturePackDescription();

                for (ResourcePackListEntry compareEntry : parent.getSelectedResourcePacks()) {
                    if (compareEntry instanceof ResourcePackListEntryFound) {
                        if (((ResourcePackListEntryFound) compareEntry).func_148318_i().equals(repoEntry)) {
                            return false;
                        }
                    }
                }
            } else if (entry instanceof FolderResourcePackEntry) {
                FolderResourcePackEntry folder = (FolderResourcePackEntry) entry;
                name = folder.func_148312_b();
                description = folder.func_148311_a();
            }

            String fullText = EnumChatFormatting.getTextWithoutFormattingCodes(name + " " + description).toLowerCase();
            return fullText.contains(filter);
        }).collect(Collectors.toList());
    }

    public boolean hasChanged() {
        // Implementar lógica de comparação se necessário
        return true; 
    }

    private void populate(List<ResourcePackListEntry> entries) {
        for (ResourcePackListEntry entry : this.entries) {
            if (entry instanceof FolderResourcePackEntry) {
                entries.add(entry);
            } else {
                entries.add(entry);
            }
        }
    }

    public void up() {
        if(delegate != null && delegate.delegate != null) {
            delegate.up();
            return;
        }
        delegate = null;
    }

    @Override
    protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
        GlStateManager.enableBlend();
        super.drawSlot(entryID, p_180791_2_, p_180791_3_, p_180791_4_, mouseXIn, mouseYIn);
    }
}